package com.skillbox.unsplash.data.images.retrofit

import com.skillbox.unsplash.common.extensions.toDetailImageItem
import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.images.retrofit.model.image.RemoteImage
import com.skillbox.unsplash.feature.images.detail.data.DetailImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitImageRepositoryImpl(
    private val network: Network,
) : RetrofitImageRepository {

    override suspend fun getImages(searchQuery: String?, pageNumber: Int, pageSize: Int): List<RemoteImage> {
        return withContext(Dispatchers.IO) {
            if (searchQuery == null) {
                network.unsplashImagesApi.getImages(pageNumber, pageSize)
            } else {
                network.unsplashImagesApi.searchImages(searchQuery, pageNumber, pageSize).result
            }
        }
    }

    override suspend fun setLike(imageId: String) {
        withContext(Dispatchers.IO) {
            network.unsplashImagesApi.setLike(imageId)
        }
    }

    override suspend fun removeLike(imageId: String) {
        withContext(Dispatchers.IO) {
            network.unsplashImagesApi.removeLike(imageId)
        }
    }

    override suspend fun getImageDetailInfo(imageId: String): DetailImageItem {
        return withContext(Dispatchers.IO) {
            network.unsplashImagesApi.getImageDetailInfo(imageId).toDetailImageItem(
                "stub",
                "stub"
            )
        }
    }
}