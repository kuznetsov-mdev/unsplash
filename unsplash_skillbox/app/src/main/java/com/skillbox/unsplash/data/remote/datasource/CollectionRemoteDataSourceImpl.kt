package com.skillbox.unsplash.data.remote.datasource

import com.skillbox.unsplash.common.UnsplashResponse
import com.skillbox.unsplash.data.remote.CollectionRemoteDataSourceApi
import com.skillbox.unsplash.data.remote.dto.CollectionDto
import com.skillbox.unsplash.data.remote.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class CollectionRemoteDataSourceImpl(
    private val network: Network,
) : CollectionRemoteDataSourceApi {

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


}