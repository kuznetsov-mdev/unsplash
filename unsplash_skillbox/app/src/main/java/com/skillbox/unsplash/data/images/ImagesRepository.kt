package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.data.images.retrofit.model.RemoteImage
import com.skillbox.unsplash.util.toRemoteImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber


class ImagesRepository(
    private val imagesInternalStorageDataSource: ImagesInternalStorageDataSource,
    private val imagesLocalDataSource: ImagesLocalDataSource,
    private val imagesRemoteDataSource: ImagesRemoteDataSource
) {
    suspend fun fetchImages(pageNumber: Int, pageSize: Int, isNetworkAvailable: Boolean): List<RemoteImage> {
        return if (isNetworkAvailable) {
            Timber.d("Get images from Network")
            imagesRemoteDataSource.fetchImages(pageNumber, pageSize)
                .also {
                    CoroutineScope(Dispatchers.IO).launch {
                        it.forEach { remoteImage ->
                            imagesInternalStorageDataSource.saveImagePreview(remoteImage.id, remoteImage.urls.thumb)
                            imagesInternalStorageDataSource.saveUserAvatar(remoteImage.user.id, remoteImage.user.profileImage.medium)
                        }
                        imagesLocalDataSource.saveRemoteImages(it)
                    }
                }
        } else {
            Timber.d("Get images from DB")
            imagesLocalDataSource.loadAllImages().map { it.toRemoteImage() }
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
    }
}