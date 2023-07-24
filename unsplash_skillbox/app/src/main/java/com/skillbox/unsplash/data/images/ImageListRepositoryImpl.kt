package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.images.model.RemoteImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageListRepositoryImpl @Inject constructor(
    private val network: Network
) : ImageListRepositoryApi {

    override suspend fun getImageList(imagePerPage: Int): List<RemoteImage> {
        //Retrofit runs query on background thread
        return withContext(Dispatchers.IO) {
            network.unsplashApi.searchImages(imagePerPage)
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