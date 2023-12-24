package com.skillbox.unsplash.data.images.retrofit

import com.skillbox.unsplash.common.extensions.toDetailImageItem
import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.images.retrofit.model.ImageDto
import com.skillbox.unsplash.feature.images.detail.model.ImageDetailUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RetrofitImageRepositoryApiImpl(
    private val network: Network,
) : RetrofitImageRepositoryApi {

    override suspend fun getImages(pageNumber: Int, pageSize: Int): List<ImageDto> {
        return withContext(Dispatchers.IO) {
            try {
                network.imagesApi.getImages(pageNumber, pageSize)
            } catch (e: HttpException) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    override suspend fun searchImages(searchQuery: String, pageNumber: Int, pageSize: Int): List<ImageDto> {
        return withContext(Dispatchers.IO) {
            try {
                network.imagesApi.searchImages(searchQuery, pageNumber, pageSize).result
            } catch (e: HttpException) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    override suspend fun getCollectionImages(collectionId: String, pageNumber: Int, pageSize: Int): List<ImageDto> {
        return withContext(Dispatchers.IO) {
            try {
                network.imagesApi.getCollectionImages(collectionId, pageNumber, pageSize)
            } catch (e: HttpException) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    override suspend fun setLike(imageId: String) {
        withContext(Dispatchers.IO) {
            try {
                network.imagesApi.setLike(imageId)
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun removeLike(imageId: String) {
        withContext(Dispatchers.IO) {
            try {
                network.imagesApi.removeLike(imageId)
            } catch (e: HttpException) {
                e.printStackTrace()
            }
        }
    }

    override suspend fun getImageDetailInfo(imageId: String): ImageDetailUiModel {
        return withContext(Dispatchers.IO) {
            try {
                network.imagesApi.getImageDetailInfo(imageId).toDetailImageItem(
                    "stub",
                    "stub"
                )
            } catch (e: HttpException) {
                e.printStackTrace()
                throw Error(e)
            }

        }
    }

    override suspend fun getUserImages(userName: String, pageNumber: Int, pageSize: Int): List<ImageDto> {
        return withContext(Dispatchers.IO) {
            try {
                network.imagesApi.getUserImages(userName, pageNumber, pageSize)
            } catch (e: HttpException) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    override suspend fun getLikedUserImages(userName: String, pageNumber: Int, pageSize: Int): List<ImageDto> {
        return withContext(Dispatchers.IO) {
            try {
                network.imagesApi.getLikedUserImages(userName, pageNumber, pageSize)
            } catch (e: HttpException) {
                e.printStackTrace()
                emptyList();
            }
        }
    }
}