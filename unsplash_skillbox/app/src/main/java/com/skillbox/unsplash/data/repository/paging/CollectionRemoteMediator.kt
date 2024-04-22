package com.skillbox.unsplash.data.repository.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.skillbox.unsplash.common.UnsplashResponse
import com.skillbox.unsplash.common.extensions.toRoomEntity
import com.skillbox.unsplash.data.local.datasource.CollectionsLocalDataSourceApi
import com.skillbox.unsplash.data.local.db.entities.relations.CollectionWithUserAndImagesEntity
import com.skillbox.unsplash.data.remote.dto.CollectionDto
import com.skillbox.unsplash.data.remote.network.Network
import com.skillbox.unsplash.data.repository.DeviceStorageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File

@OptIn(ExperimentalPagingApi::class)
class CollectionRemoteMediator(
    private val network: Network,
    private val collectionsLocalDataSource: CollectionsLocalDataSourceApi,
    private val deviceStorageRepository: DeviceStorageRepository,
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
                collectionsLocalDataSource.refresh(collections)
                removeImagesFromDisk(collections)
            } else {
                collectionsLocalDataSource.insertAll(collections)
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
                getAll(pageIndex, pageSize)
            } else {
                getUserCollections(userName, pageIndex, pageSize)
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
        deviceStorageRepository.saveImageToInternalStorage(model.coverPhoto.id, model.coverPhoto.urls.small, "thumbnails")
        deviceStorageRepository.saveImageToInternalStorage(model.user.id, model.user.profileImage.medium, "avatars")
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
            deviceStorageRepository.removeCachedImages(imagesLinks)
        }
    }

    private suspend fun getUserCollections(userName: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<CollectionDto>> {
        return withContext(Dispatchers.IO) {
            try {
                UnsplashResponse.Success(network.collectionsApi.getUserCollection(userName, pageNumber, pageSize))
            } catch (t: Throwable) {
                Timber.tag("${this.javaClass.simpleName} - UnsplashResponse").d(t)
                UnsplashResponse.Error(t)
            }
        }
    }

    private suspend fun getAll(pageNumber: Int, pageSize: Int): UnsplashResponse<List<CollectionDto>> {
        return withContext(Dispatchers.IO) {
            try {
                UnsplashResponse.Success(network.collectionsApi.getCollections(pageNumber, pageSize))
            } catch (t: Throwable) {
                Timber.tag("${this.javaClass.simpleName} - UnsplashResponse").d(t)
                UnsplashResponse.Error(t)
            }
        }
    }
}