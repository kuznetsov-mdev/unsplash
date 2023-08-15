package com.skillbox.unsplash.data.images

import android.content.Context
import com.skillbox.unsplash.data.images.retrofit.model.RemoteImage
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import com.skillbox.unsplash.util.toImageItem
import com.skillbox.unsplash.util.toImageWithAuthorEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.io.File


class ImagesRepository(
    private val context: Context,
    private val imagesInternalStorageDataSource: ImagesInternalStorageDataSource,
    private val imagesLocalDataSource: ImagesLocalDataSource,
    private val imagesRemoteDataSource: ImagesRemoteDataSource
) {
    suspend fun fetchImages(pageNumber: Int, pageSize: Int, isNetworkAvailable: Boolean): List<ImageItem> {
        return if (isNetworkAvailable) {
            Timber.d("Get images from Network")
            convertToImageItem(imagesRemoteDataSource.fetchImages(pageNumber, pageSize))
                .also { saveImageDataToLocalStorage(it) }
        } else {
            Timber.d("Get images from DB")
            imagesLocalDataSource.loadAllImages().map { it.toImageItem() }
        }
    }

    suspend fun setLike(imageId: String) {
        imagesRemoteDataSource.setLike(imageId)
    }

    suspend fun removeLike(imageId: String) {
        imagesRemoteDataSource.removeLike(imageId)
    }

    suspend fun removeImages() {
        imagesLocalDataSource.removeImages()
        imagesInternalStorageDataSource.clearAllImages()
    }

    private suspend fun saveImageDataToLocalStorage(imageItemList: List<ImageItem>) {
        CoroutineScope(Dispatchers.IO).launch {
            imageItemList.forEach { saveImageToInternalStorage(it) }
            imagesLocalDataSource.saveImages(imageItemList.map { it.toImageWithAuthorEntity() })
        }
    }

    private suspend fun saveImageToInternalStorage(imageItem: ImageItem) {
        imagesInternalStorageDataSource.saveImagePreview(imageItem.id, imageItem.imageUrl)
        imagesInternalStorageDataSource.saveUserAvatar(imageItem.authorId, imageItem.authorAvatarUrl)
    }

    private fun convertToImageItem(remoteImageList: List<RemoteImage>): List<ImageItem> {
        return remoteImageList.map { remoteImage ->
            runBlocking(Dispatchers.IO) {
                remoteImage.toImageItem(
                    File(context.cacheDir.path).resolve("thumbnails").resolve("${remoteImage.id}.jpg").toString(),
                    File(context.cacheDir.path).resolve("avatars").resolve("${remoteImage.user.id}.jpg").toString()
                )
            }
        }
    }
}