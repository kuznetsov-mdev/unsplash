package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.images.model.RemoteImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitImagesRepositoryImpl @Inject constructor(
    private val network: Network
) : ImagesRepository {

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

    override suspend fun getImageList(pageNumber: Int, pageSize: Int): List<RemoteImage> {
        //Retrofit runs query on background thread
        return withContext(Dispatchers.IO) {
            network.unsplashApi.searchImages(pageNumber, pageSize)
        }
    }

}