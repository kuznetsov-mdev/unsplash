package com.skillbox.unsplash.data.images

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.work.WorkInfo
import com.skillbox.unsplash.data.images.storage.ImageRemoteDataSource
import com.skillbox.unsplash.data.images.storage.ImageStorageDataSource
import com.skillbox.unsplash.data.images.storage.ImagesLocalDataSource
import com.skillbox.unsplash.feature.images.detail.data.DetailImageItem
import com.skillbox.unsplash.feature.images.list.data.ImageItem
import com.skillbox.unsplash.feature.images.list.paging.ImagePageSource
import com.skillbox.unsplash.util.toImageWithAuthorEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class ImagesRepository(
    private val imageStorageDataSource: ImageStorageDataSource,
    private val imagesLocalDataSource: ImagesLocalDataSource,
    private val imageRemoteDataSource: ImageRemoteDataSource
) {
    suspend fun search(query: String, pageSize: Int, isNetworkAvailable: Boolean): Flow<PagingData<ImageItem>> {
        return getPagingDataFlow(query, pageSize, isNetworkAvailable).onEach { imageItem ->
            val images: MutableList<ImageItem> = mutableListOf()
            if (isNetworkAvailable) {
                imageItem.map { images.add(ImageItem(it.image, it.author)) }
            }

            if (images.size == pageSize) {
                saveImageDataToLocalStorage(images)
            }
        }
    }

    suspend fun getImageDetailInfo(imageId: String): DetailImageItem {
        return imageRemoteDataSource.getImageDetailInfo(imageId)
    }

    suspend fun setLike(imageId: String) {
        imageRemoteDataSource.setLike(imageId)
    }

    suspend fun removeLike(imageId: String) {
        imageRemoteDataSource.removeLike(imageId)
    }

    suspend fun removeImages() {
        imagesLocalDataSource.removeImages()
        imageStorageDataSource.removeAllFromInternalStorage()
    }

    private suspend fun saveImageDataToLocalStorage(imageItemList: List<ImageItem>) {
        CoroutineScope(Dispatchers.IO).launch {
            imageItemList.forEach { saveImageToStorage(it) }
            imagesLocalDataSource.saveImages(imageItemList.map { it.toImageWithAuthorEntity() })
        }
    }

    private suspend fun saveImageToStorage(imageItem: ImageItem) {
        imageStorageDataSource.saveImageToInternalStorage(imageItem.image.id, imageItem.image.url, "thumbnails")
        imageStorageDataSource.saveImageToInternalStorage(imageItem.author.id, imageItem.author.avatarUrl, "avatars")
    }

    fun startImageSavingToGalleryWork(name: String, url: String): LiveData<WorkInfo> {
        return imageStorageDataSource.startImageSavingToExternalStorageWork(name, url)
    }

    private fun getPagingDataFlow(query: String, pageSize: Int, isNetworkAvailable: Boolean): Flow<PagingData<ImageItem>> {
        val imageDataSource = if (isNetworkAvailable) imageRemoteDataSource else imagesLocalDataSource
        return Pager(
            config = PagingConfig(pageSize),
            pagingSourceFactory = {
                ImagePageSource(imageDataSource, query, pageSize)
            }
        ).flow
    }
}