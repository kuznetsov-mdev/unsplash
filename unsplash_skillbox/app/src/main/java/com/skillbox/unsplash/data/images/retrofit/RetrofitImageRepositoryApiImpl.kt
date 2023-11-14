package com.skillbox.unsplash.data.images.retrofit

import com.skillbox.unsplash.common.extensions.toDetailImageItem
import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.images.retrofit.model.ImageDto
import com.skillbox.unsplash.feature.images.detail.model.ImageDetailUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitImageRepositoryApiImpl(
    private val network: Network,
) : RetrofitImageRepositoryApi {

    override suspend fun getImages(searchQuery: String?, pageNumber: Int, pageSize: Int): List<ImageDto> {
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

    override suspend fun getImageDetailInfo(imageId: String): ImageDetailUiModel {
        return withContext(Dispatchers.IO) {
            network.imagesApi.getImageDetailInfo(imageId).toDetailImageItem(
                "stub",
                "stub"
            )
        }
    }
}