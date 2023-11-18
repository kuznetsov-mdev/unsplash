package com.skillbox.unsplash.data.images.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.skillbox.unsplash.common.extensions.toRoomImageEntity
import com.skillbox.unsplash.data.common.SearchCondition
import com.skillbox.unsplash.data.common.storage.DiskImageRepository
import com.skillbox.unsplash.data.images.retrofit.RetrofitImageRepositoryApi
import com.skillbox.unsplash.data.images.retrofit.model.ImageDto
import com.skillbox.unsplash.data.images.room.RoomImageRepositoryApi
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithUserEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

@OptIn(ExperimentalPagingApi::class)
class ImageRemoteMediator(
    private val condition: SearchCondition,
    private val roomImageRepository: RoomImageRepositoryApi,
    private val retrofitImageRepository: RetrofitImageRepositoryApi,
    private val diskImageRepository: DiskImageRepository,
    private val context: Context
) : RemoteMediator<Int, ImageWithUserEntity>() {
    private var pageIndex = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageWithUserEntity>
    ): MediatorResult {

        pageIndex = getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)
        val pageSize = state.config.pageSize

        return try {
            val images: List<ImageWithUserEntity> = getImages(pageSize, pageIndex)

            if (loadType == LoadType.REFRESH) {
                roomImageRepository.refresh(condition, images)
                removeImagesFromDisk(images)
            } else {
                roomImageRepository.insertAll(condition, images)
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

    private suspend fun getImages(pageSize: Int, pageNumber: Int): List<ImageWithUserEntity> {
        val searchResult: List<ImageDto> = when (condition) {
            is SearchCondition.Empty ->
                retrofitImageRepository.getImages(pageNumber, pageSize)

            is SearchCondition.SearchString ->
                retrofitImageRepository.searchImages(condition.searchQuery, pageNumber, pageSize)

            is SearchCondition.CollectionImages ->
                retrofitImageRepository.getCollectionImages(condition.collectionId, pageNumber, pageSize)

            else -> error("Not implemented yet")
        }

        saveImageDataOnDisk(searchResult)
        return convertToImageWithAuthorEntity(searchResult)
    }

    private suspend fun saveImageDataOnDisk(models: List<ImageDto>) {
        CoroutineScope(Dispatchers.IO).launch {
            models.forEach { saveImageToInternalStorage(it) }
        }
    }

    private suspend fun removeImagesFromDisk(images: List<ImageWithUserEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            val imagesLinks = mutableListOf<String>()
            images.forEach { img ->
                imagesLinks.add(img.image.cachedPreview)
                imagesLinks.add(img.user.cachedProfileImage)
            }
            diskImageRepository.removeCachedImages(imagesLinks)
        }
    }

    private suspend fun saveImageToInternalStorage(model: ImageDto) {
        diskImageRepository.saveImageToInternalStorage(model.id, model.urls.thumb, "thumbnails")
        diskImageRepository.saveImageToInternalStorage(model.user.id, model.user.profileImage.medium, "avatars")
    }

    private fun convertToImageWithAuthorEntity(models: List<ImageDto>): List<ImageWithUserEntity> {
        return models.map { remoteImage ->
            runBlocking(Dispatchers.IO) {
                remoteImage.toRoomImageEntity(
                    File(context.cacheDir.path).resolve("thumbnails").resolve("${remoteImage.id}.jpg").toString(),
                    File(context.cacheDir.path).resolve("avatars").resolve("${remoteImage.user.id}.jpg").toString(),
                    getSearchQueryFromSearchCondition()
                )
            }
        }
    }

    private fun getSearchQueryFromSearchCondition(): String {
        return when (condition) {
            is SearchCondition.SearchString -> condition.searchQuery
            else -> "";
        }
    }
}