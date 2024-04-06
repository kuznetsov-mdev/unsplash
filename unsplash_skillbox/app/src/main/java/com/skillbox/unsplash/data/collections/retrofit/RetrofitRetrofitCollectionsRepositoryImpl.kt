package com.skillbox.unsplash.data.collections.retrofit

import com.skillbox.unsplash.data.common.retrofit.UnsplashResponse
import com.skillbox.unsplash.data.network.Network
import com.skillbox.unsplash.domain.model.remote.CollectionDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class RetrofitRetrofitCollectionsRepositoryImpl @Inject constructor(
    private val network: Network
) : RetrofitCollectionsRepositoryApi {

    override suspend fun getAll(pageNumber: Int, pageSize: Int): UnsplashResponse<List<CollectionDto>> {
        return withContext(Dispatchers.IO) {
            try {
                UnsplashResponse.Success(network.collectionsApi.getCollections(pageNumber, pageSize))
            } catch (t: Throwable) {
                Timber.tag("${this.javaClass.simpleName} - UnsplashResponse").d(t)
                UnsplashResponse.Error(t)
            }
        }
    }

    override suspend fun getUserCollections(userName: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<CollectionDto>> {
        return withContext(Dispatchers.IO) {
            try {
                UnsplashResponse.Success(network.collectionsApi.getUserCollection(userName, pageNumber, pageSize))
            } catch (t: Throwable) {
                Timber.tag("${this.javaClass.simpleName} - UnsplashResponse").d(t)
                UnsplashResponse.Error(t)
            }
        }
    }
}