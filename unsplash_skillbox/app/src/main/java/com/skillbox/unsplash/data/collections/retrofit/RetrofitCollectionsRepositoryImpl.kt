package com.skillbox.unsplash.data.collections.retrofit

import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.collections.retrofit.model.CollectionRetrofitModel
import javax.inject.Inject

class RetrofitCollectionsRepositoryImpl @Inject constructor(
    private val network: Network
) : RetrofitCollectionsRepository {

    override suspend fun getAll(pageNumber: Int, pageSize: Int): List<CollectionRetrofitModel> {
        return network.collectionsApi.getCollections(pageNumber, pageSize);
    }
}