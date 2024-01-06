package com.skillbox.unsplash.data.collections.retrofit

import com.skillbox.unsplash.data.collections.retrofit.model.CollectionDto
import com.skillbox.unsplash.data.common.retrofit.UnsplashResponse

interface RetrofitCollectionsRepositoryApi {

    suspend fun getAll(pageNumber: Int, pageSize: Int): UnsplashResponse<List<CollectionDto>>

    suspend fun getUserCollections(userName: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<CollectionDto>>
}