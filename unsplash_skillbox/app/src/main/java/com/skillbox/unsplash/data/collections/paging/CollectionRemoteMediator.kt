package com.skillbox.unsplash.data.collections.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.skillbox.unsplash.common.extensions.toRoomEntity
import com.skillbox.unsplash.data.collections.retrofit.RetrofitCollectionsRepositoryApi
import com.skillbox.unsplash.data.collections.retrofit.model.CollectionDto
import com.skillbox.unsplash.data.collections.room.RoomCollectionsRepositoryApi
import com.skillbox.unsplash.data.collections.room.model.relations.CollectionWithUserAndImagesEntity
import com.skillbox.unsplash.data.common.retrofit.UnsplashResponse
import com.skillbox.unsplash.data.common.storage.DiskImageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

@OptIn(ExperimentalPagingApi::class)
class CollectionRemoteMediator(
    private val retrofitCollectionsRepository: RetrofitCollectionsRepositoryApi,
    private val roomCollectionsRepository: RoomCollectionsRepositoryApi,
    private val diskImageRepository: DiskImageRepository,
    private val context: Context,
    private val userName: String?
) : RemoteMediator<Int, CollectionWithUserAndImagesEntity>() {
    private var pageIndex = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CollectionWithUserAndImagesEntity>
    ): MediatorResult {
        pageIndex = getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)
        val pageSize = state.config.pageSize

        return try {
            val collections: List<CollectionWithUserAndImagesEntity> = getCollections(pageIndex, pageSize)

            if (loadType == LoadType.REFRESH) {
                roomCollectionsRepository.refresh(collections)
                removeImagesFromDisk(collections)
            } else {
                roomCollectionsRepository.insertAll(collections)
            }
            MediatorResult.Success(endOfPaginationReached = collections.size < pageSize)
        } catch (t: Throwable) {
            MediatorResult.Error(t)
        }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        return when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> null
            LoadType.APPEND -> ++pageIndex
        }
    }

    private suspend fun getCollections(pageIndex: Int, pageSize: Int): List<CollectionWithUserAndImagesEntity> {
        val collections: UnsplashResponse<List<CollectionDto>> =
            if (userName == null) {
                retrofitCollectionsRepository.getAll(pageIndex, pageSize)
            } else {
                retrofitCollectionsRepository.getUserCollections(userName, pageIndex, pageSize)
            }

        return when (collections) {
            is UnsplashResponse.Error -> throw collections.throwable
            is UnsplashResponse.Success -> {
                saveCollectionImageDataOnDisk(collections.data)
                convertToImageWithAuthorEntity(collections.data)
            }
        }
    }

    private suspend fun saveCollectionImageDataOnDisk(models: List<CollectionDto>) {
        CoroutineScope(Dispatchers.IO).launch {
            models.forEach { saveImageToInternalStorage(it) }
        }
    }

    private suspend fun saveImageToInternalStorage(model: CollectionDto) {
        diskImageRepository.saveImageToInternalStorage(model.coverPhoto.id, model.coverPhoto.urls.small, "thumbnails")
        diskImageRepository.saveImageToInternalStorage(model.user.id, model.user.profileImage.medium, "avatars")
    }

    private fun convertToImageWithAuthorEntity(models: List<CollectionDto>): List<CollectionWithUserAndImagesEntity> {
        return models.map { retrofitCollection ->
            runBlocking(Dispatchers.IO) {
                retrofitCollection.toRoomEntity(
                    File(context.cacheDir.path)
                        .resolve("thumbnails")
                        .resolve("${retrofitCollection.coverPhoto.id}.jpg")
                        .toString(),
                    File(context.cacheDir.path)
                        .resolve("avatars")
                        .resolve("${retrofitCollection.user.id}.jpg")
                        .toString()
                )
            }
        }
    }

    private suspend fun removeImagesFromDisk(collections: List<CollectionWithUserAndImagesEntity>) {
        CoroutineScope(Dispatchers.IO).launch {
            val imagesLinks = mutableListOf<String>()
            collections.forEach { collection ->
                imagesLinks.add(collection.collectionWithImages.collection.cachedCoverPhoto)
            }
            diskImageRepository.removeCachedImages(imagesLinks)
        }
    }
}