package com.skillbox.unsplash.data.collections.retrofit

import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.collections.retrofit.model.CollectionDto
import javax.inject.Inject

class RetrofitRetrofitCollectionsRepositoryImpl @Inject constructor(
    private val network: Network
) : RetrofitCollectionsRepositoryApi {

    override suspend fun getAll(pageNumber: Int, pageSize: Int): List<CollectionDto> {
        return network.collectionsApi.getCollections(pageNumber, pageSize)
    }

    override suspend fun getUserCollections(userName: String, pageNumber: Int, pageSize: Int): List<CollectionDto> {
        return network.collectionsApi.getUserCollection(userName, pageNumber, pageSize)
    }
}