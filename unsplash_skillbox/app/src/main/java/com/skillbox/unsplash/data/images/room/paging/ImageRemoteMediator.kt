package com.skillbox.unsplash.data.images.room.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.skillbox.unsplash.common.extensions.toRoomImageEntity
import com.skillbox.unsplash.data.images.retrofit.RetrofitImageRepository
import com.skillbox.unsplash.data.images.retrofit.model.ImageRetrofitModel
import com.skillbox.unsplash.data.images.room.RoomImageRepository
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithUserRoomModel
import com.skillbox.unsplash.data.images.storage.DiskImageRepository
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
) : RemoteMediator<Int, ImageWithUserRoomModel>() {
    private var pageIndex = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageWithUserRoomModel>
    ): MediatorResult {

        pageIndex = getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)
        val pageSize = state.config.pageSize

        return try {
            val images: List<ImageWithUserRoomModel> = getImages(pageSize, pageIndex)

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

    private suspend fun getImages(pageSize: Int, pageNumber: Int): List<ImageWithUserRoomModel> {
        val searchResult = retrofitImageRepository.getImages(query, pageNumber, pageSize);
        saveImageDataOnDisk(searchResult)
        return convertToImageWithAuthorEntity(searchResult)
    }

    private suspend fun saveImageDataOnDisk(models: List<ImageRetrofitModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            models.forEach { saveImageToInternalStorage(it) }
        }
    }

    private suspend fun removeImagesFromDisk(images: List<ImageWithUserRoomModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            val imagesLinks = mutableListOf<String>()
            images.forEach { img ->
                imagesLinks.add(img.image.cachedPreview)
                imagesLinks.add(img.user.cachedProfileImage)
            }
            diskImageRepository.removeCachedImages(imagesLinks)
        }
    }

    private suspend fun saveImageToInternalStorage(model: ImageRetrofitModel) {
        diskImageRepository.saveImageToInternalStorage(model.id, model.urls.thumb, "thumbnails")
        diskImageRepository.saveImageToInternalStorage(model.user.id, model.user.profileImage.medium, "avatars")
    }

    private fun convertToImageWithAuthorEntity(models: List<ImageRetrofitModel>): List<ImageWithUserRoomModel> {
        return models.map { remoteImage ->
            runBlocking(Dispatchers.IO) {
                remoteImage.toRoomImageEntity(
                    File(context.cacheDir.path).resolve("thumbnails").resolve("${remoteImage.id}.jpg").toString(),
                    File(context.cacheDir.path).resolve("avatars").resolve("${remoteImage.user.id}.jpg").toString(),
                    query ?: ""
                )
            }
        }
    }
}