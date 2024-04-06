package com.skillbox.unsplash.domain.api.repository

import com.skillbox.unsplash.common.UnsplashResponse
import com.skillbox.unsplash.data.remote.dto.CollectionDto

interface RetrofitCollectionsRepositoryApi {

    suspend fun getAll(pageNumber: Int, pageSize: Int): UnsplashResponse<List<CollectionDto>>

    suspend fun getUserCollections(userName: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<CollectionDto>>
}