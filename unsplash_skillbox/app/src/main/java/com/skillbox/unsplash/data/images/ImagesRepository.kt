package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.data.images.retrofit.model.RemoteImage
import com.skillbox.unsplash.util.toRemoteImage


class ImagesRepository(
    private val imagesLocalDataSource: ImagesLocalDataSource,
    private val imagesRemoteDataSource: ImagesRemoteDataSource
) {

    suspend fun fetchImages(pageNumber: Int, pageSize: Int, isThereInternetConnection: Boolean): List<RemoteImage> {
        return if (isThereInternetConnection) {
            imagesRemoteDataSource.fetchImages(pageNumber, pageSize)
                .also {
                    imagesLocalDataSource.saveRemoteImages(it)
                }
        } else {
            imagesLocalDataSource.loadAllImages().map { it.toRemoteImage() }
        }
    }


    suspend fun setLike(imageId: String) {
        imagesRemoteDataSource.setLike(imageId)
    }

    suspend fun removeLike(imageId: String) {
        imagesRemoteDataSource.removeLike(imageId)
    }
}