package com.skillbox.unsplash.data.images.retrofit

import com.skillbox.unsplash.common.extensions.toDetailImageItem
import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.model.retrofit.image.RetrofitImageModel
import com.skillbox.unsplash.feature.images.detail.model.UiImageDetailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitImageRepositoryImpl(
    private val network: Network,
) : RetrofitImageRepository {

    override suspend fun getImages(searchQuery: String?, pageNumber: Int, pageSize: Int): List<RetrofitImageModel> {
        return withContext(Dispatchers.IO) {
            if (searchQuery == null) {
                network.imagesApi.getImages(pageNumber, pageSize)
            } else {
                network.imagesApi.searchImages(searchQuery, pageNumber, pageSize).result
            }
        }
    }

    override suspend fun setLike(imageId: String) {
        withContext(Dispatchers.IO) {
            network.imagesApi.setLike(imageId)
        }
    }

    override suspend fun removeLike(imageId: String) {
        withContext(Dispatchers.IO) {
            network.imagesApi.removeLike(imageId)
        }
    }

    override suspend fun getImageDetailInfo(imageId: String): UiImageDetailModel {
        return withContext(Dispatchers.IO) {
            network.imagesApi.getImageDetailInfo(imageId).toDetailImageItem(
                "stub",
                "stub"
            )
        }
    }
}