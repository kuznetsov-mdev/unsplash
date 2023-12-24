package com.skillbox.unsplash.data.collections.retrofit

import com.skillbox.unsplash.data.collections.retrofit.model.CollectionDto

interface RetrofitCollectionsRepositoryApi {

    suspend fun getAll(pageNumber: Int, pageSize: Int): List<CollectionDto>

    suspend fun getUserCollections(userName: String, pageNumber: Int, pageSize: Int): List<CollectionDto>
}