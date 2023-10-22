package com.skillbox.unsplash.data.images.room.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.skillbox.unsplash.common.extensions.toRoomImageEntity
import com.skillbox.unsplash.data.images.retrofit.model.image.RemoteImage
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity
import com.skillbox.unsplash.data.images.storage.DiskImageRepository
import com.skillbox.unsplash.data.images.storage.RetrofitImageRepository
import com.skillbox.unsplash.data.images.storage.RoomImageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

@OptIn(ExperimentalPagingApi::class)
class ImageRemoteMediator(
    private val query: String?,
    private val roomImageRepository: RoomImageRepository,
    private val retrofitImageRepository: RetrofitImageRepository,
    private val diskImageRepository: DiskImageRepository,
    private val context: Context
) : RemoteMediator<Int, ImageWithAuthorEntity>() {
    private var pageIndex = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageWithAuthorEntity>
    ): MediatorResult {

        pageIndex = getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)
        val pageSize = state.config.pageSize

        return try {
            val images: List<ImageWithAuthorEntity> = getImages(pageSize, pageIndex)

            if (loadType == LoadType.REFRESH) {
                roomImageRepository.refresh(query, images)
                removeImagesFromDisk(images)
            } else {
                roomImageRepository.insertAll(images)
            }
            MediatorResult.Success(endOfPaginationReached = images.size < pageSize)
        } catch (e: Error) {
            MediatorResult.Error(e)
        }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        return when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> null
            LoadType.APPEND -> ++pageIndex
        }
    }

    private suspend fun getImages(pageSize: Int, pageNumber: Int): List<ImageWithAuthorEntity> {
        val searchResult = retrofitImageRepository.getImages(query, pageNumber, pageSize);
        saveImageDataOnDisk(searchResult)
        return convertToImageWithAuthorEntity(searchResult)
    }

    private suspend fun saveImageDataOnDisk(remoteImages: List<RemoteImage>) {
        CoroutineScope(Dispatchers.IO).launch {
            remoteImages.forEach { saveImageToInternalStorage(it) }
        }
    }

    private suspend fun removeImagesFromDisk(images: List<ImageWithAuthorEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            val imagesLinks = mutableListOf<String>()
            images.forEach { img ->
                imagesLinks.add(img.image.cachedPreview)
                imagesLinks.add(img.author.cachedProfileImage)
            }
            diskImageRepository.removeCachedImages(imagesLinks)
        }
    }

    private suspend fun saveImageToInternalStorage(remoteImage: RemoteImage) {
        diskImageRepository.saveImageToInternalStorage(remoteImage.id, remoteImage.urls.thumb, "thumbnails")
        diskImageRepository.saveImageToInternalStorage(remoteImage.user.id, remoteImage.user.profileImage.medium, "avatars")
    }

    private fun convertToImageWithAuthorEntity(remoteImageList: List<RemoteImage>): List<ImageWithAuthorEntity> {
        return remoteImageList.map { remoteImage ->
            runBlocking(Dispatchers.IO) {
                remoteImage.toRoomImageEntity(
                    File(context.cacheDir.path).resolve("thumbnails").resolve("${remoteImage.id}.jpg").toString(),
                    File(context.cacheDir.path).resolve("avatars").resolve("${remoteImage.user.id}.jpg").toString()
                )
            }
        }
    }
}