package com.skillbox.unsplash.data.collections.room.paging

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.skillbox.unsplash.common.extensions.toRoomEntity
import com.skillbox.unsplash.data.collections.retrofit.RetrofitCollectionsRepository
import com.skillbox.unsplash.data.collections.retrofit.model.CollectionRetrofitModel
import com.skillbox.unsplash.data.collections.room.RoomCollectionsRepository
import com.skillbox.unsplash.data.collections.room.model.relations.CollectionWithUserAndImagesRoomModel
import com.skillbox.unsplash.data.images.storage.DiskImageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File

@OptIn(ExperimentalPagingApi::class)
class CollectionRemoteMediator(
    private val retrofitCollectionsRepository: RetrofitCollectionsRepository,
    private val roomCollectionsRepository: RoomCollectionsRepository,
    private val diskImageRepository: DiskImageRepository,
    private val context: Context
) : RemoteMediator<Int, CollectionWithUserAndImagesRoomModel>() {
    private var pageIndex = 1

    override suspend fun load(loadType: LoadType, state: PagingState<Int, CollectionWithUserAndImagesRoomModel>): MediatorResult {
        pageIndex = getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)
        val pageSize = state.config.pageSize

        return try {
            val collections: List<CollectionWithUserAndImagesRoomModel> = getCollections(pageIndex, pageSize)

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

    private suspend fun getCollections(pageIndex: Int, pageSize: Int): List<CollectionWithUserAndImagesRoomModel> {
        val collections: List<CollectionRetrofitModel> = retrofitCollectionsRepository.getAll(pageIndex, pageSize)
        saveImageDataOnDisk(collections)
        return convertToImageWithAuthorEntity(collections)
    }

    private suspend fun saveImageDataOnDisk(models: List<CollectionRetrofitModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            models.forEach { saveImageToInternalStorage(it) }
        }
    }

    private suspend fun saveImageToInternalStorage(model: CollectionRetrofitModel) {
        diskImageRepository.saveImageToInternalStorage(model.coverPhoto.id, model.coverPhoto.urls.thumb, "thumbnails")
        diskImageRepository.saveImageToInternalStorage(model.user.id, model.user.profileImage.medium, "avatars")
    }

    private fun convertToImageWithAuthorEntity(models: List<CollectionRetrofitModel>): List<CollectionWithUserAndImagesRoomModel> {
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

    private suspend fun removeImagesFromDisk(collections: List<CollectionWithUserAndImagesRoomModel>) {
        CoroutineScope(Dispatchers.IO).launch {
            val imagesLinks = mutableListOf<String>()
            collections.forEach { collection ->
                imagesLinks.add(collection.collectionWithImages.collection.cachedCoverPhoto)
            }
            diskImageRepository.removeCachedImages(imagesLinks)
        }
    }
}