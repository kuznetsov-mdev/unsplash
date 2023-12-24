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

    override suspend fun getImages(pageNumber: Int, pageSize: Int): List<ImageDto> {
        return withContext(Dispatchers.IO) {
            network.imagesApi.getImages(pageNumber, pageSize)
        }
    }

    override suspend fun searchImages(searchQuery: String, pageNumber: Int, pageSize: Int): List<ImageDto> {
        return withContext(Dispatchers.IO) {
            network.imagesApi.searchImages(searchQuery, pageNumber, pageSize).result
        }
    }

    override suspend fun getCollectionImages(collectionId: String, pageNumber: Int, pageSize: Int): List<ImageDto> {
        return withContext(Dispatchers.IO) {
            network.imagesApi.getCollectionImages(collectionId, pageNumber, pageSize)
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

    override suspend fun getUserImages(userName: String, pageNumber: Int, pageSize: Int): List<ImageDto> {
        return withContext(Dispatchers.IO) {
            network.imagesApi.getUserImages(userName, pageNumber, pageSize)
        }
    }

    override suspend fun getLikedUserImages(userName: String, pageNumber: Int, pageSize: Int): List<ImageDto> {
        return withContext(Dispatchers.IO) {
            network.imagesApi.getLikedUserImages(userName, pageNumber, pageSize)
        }
    }
}