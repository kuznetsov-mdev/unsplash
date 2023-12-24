package com.skillbox.unsplash.data.collections.retrofit

import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.collections.retrofit.model.CollectionDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class RetrofitRetrofitCollectionsRepositoryImpl @Inject constructor(
    private val network: Network
) : RetrofitCollectionsRepositoryApi {

    override suspend fun getAll(pageNumber: Int, pageSize: Int): List<CollectionDto> {
        return withContext(Dispatchers.IO) {
            try {
                network.collectionsApi.getCollections(pageNumber, pageSize)
            } catch (e: HttpException) {
                e.printStackTrace()
                emptyList()
            }
        }
    }

    override suspend fun getUserCollections(userName: String, pageNumber: Int, pageSize: Int): List<CollectionDto> {
        return withContext(Dispatchers.IO) {
            try {
                network.collectionsApi.getUserCollection(userName, pageNumber, pageSize)
            } catch (e: HttpException) {
                e.printStackTrace()
                emptyList()
            }
        }

    }
}