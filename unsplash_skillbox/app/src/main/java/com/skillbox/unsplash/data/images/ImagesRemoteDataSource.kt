package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.data.images.retrofit.model.RemoteImage

interface ImagesRemoteDataSource {

    suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<RemoteImage>

    suspend fun setLike(imageId: String)

    suspend fun removeLike(imageId: String)
}