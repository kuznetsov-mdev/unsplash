package com.skillbox.unsplash.data.remote.datasource

import com.skillbox.unsplash.common.UnsplashResponse
import com.skillbox.unsplash.common.extensions.toDetailImageItem
import com.skillbox.unsplash.data.remote.dto.ImageDto
import com.skillbox.unsplash.data.remote.network.Network
import com.skillbox.unsplash.domain.model.detail.ImageDetailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject

class RemoteImageDataSourceImpl @Inject constructor(
    private val network: Network,
) : RemoteImageDataSourceApi {

    override suspend fun getImages(pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>> {
        return withContext(Dispatchers.IO) {
            try {
                UnsplashResponse.Success(
                    network.imagesApi.getImages(pageNumber, pageSize)
                )
            } catch (t: Throwable) {
                Timber.tag("${this.javaClass.simpleName} - UnsplashResponse").d(t)
                UnsplashResponse.Error(t)
            }
        }
    }

    override suspend fun searchImages(searchQuery: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>> {
        return withContext(Dispatchers.IO) {
            try {
                UnsplashResponse.Success(
                    network.imagesApi.searchImages(searchQuery, pageNumber, pageSize).result
                )
            } catch (t: Throwable) {
                Timber.tag("${this.javaClass.simpleName} - UnsplashResponse").d(t)
                UnsplashResponse.Error(t)
            }
        }
    }

    override suspend fun getCollectionImages(collectionId: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>> {
        return withContext(Dispatchers.IO) {
            try {
                UnsplashResponse.Success(
                    network.imagesApi.getCollectionImages(collectionId, pageNumber, pageSize)
                )
            } catch (t: Throwable) {
                Timber.tag("${this.javaClass.simpleName} - UnsplashResponse").d(t)
                UnsplashResponse.Error(t)
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

    override suspend fun getImageDetailInfo(imageId: String): ImageDetailModel {
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

    override suspend fun getUserImages(userName: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>> {
        return withContext(Dispatchers.IO) {
            try {
                UnsplashResponse.Success(
                    network.imagesApi.getUserImages(userName, pageNumber, pageSize)
                )
            } catch (t: Throwable) {
                Timber.tag("${this.javaClass.simpleName} - UnsplashResponse").d(t)
                UnsplashResponse.Error(t)
            }
        }
    }

    override suspend fun getLikedUserImages(userName: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>> {
        return withContext(Dispatchers.IO) {
            try {
                UnsplashResponse.Success(
                    network.imagesApi.getLikedUserImages(userName, pageNumber, pageSize)
                )

            } catch (t: Throwable) {
                Timber.tag("${this.javaClass.simpleName} - UnsplashResponse").d(t)
                UnsplashResponse.Error(t);
            }
        }
    }
}