package com.skillbox.unsplash.data.images.retrofit

import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.images.ImagesRemoteDataSource
import com.skillbox.unsplash.data.images.retrofit.model.RemoteImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitImagesDataSource(private val network: Network) : ImagesRemoteDataSource {

    override suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<RemoteImage> {
        return withContext(Dispatchers.IO) {
            network.unsplashApi.searchImages(pageNumber, pageSize)
        }
    }

    override suspend fun setLike(imageId: String) {
        withContext(Dispatchers.IO) {
            network.unsplashApi.setLike(imageId)
        }
    }

    override suspend fun removeLike(imageId: String) {
        withContext(Dispatchers.IO) {
            network.unsplashApi.removeLike(imageId)
        }
    }
}