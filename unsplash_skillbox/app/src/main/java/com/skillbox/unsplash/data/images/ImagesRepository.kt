package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.data.images.retrofit.model.RemoteImage

class ImagesRepository(
    private val imagesLocalDataSource: ImagesLocalDataSource,
    private val imagesRemoteDataSource: ImagesRemoteDataSource
) {

    suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<RemoteImage> =
        imagesRemoteDataSource.fetchImages(pageNumber, pageSize)

    suspend fun setLike(imageId: String) {
        imagesRemoteDataSource.setLike(imageId)
    }

    suspend fun removeLike(imageId: String) {
        imagesRemoteDataSource.removeLike(imageId)
    }
}