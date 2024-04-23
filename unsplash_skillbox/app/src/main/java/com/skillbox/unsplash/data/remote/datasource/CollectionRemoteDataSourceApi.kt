package com.skillbox.unsplash.data.remote.datasource

import com.skillbox.unsplash.common.UnsplashResponse
import com.skillbox.unsplash.data.remote.dto.CollectionDto

interface CollectionRemoteDataSourceApi {

    suspend fun getUserCollections(userName: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<CollectionDto>>

    suspend fun getAll(pageNumber: Int, pageSize: Int): UnsplashResponse<List<CollectionDto>>
}