package com.skillbox.unsplash.common.network.api

import com.skillbox.unsplash.data.collections.retrofit.model.CollectionDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionsApi {

    @GET("collections")
    suspend fun getCollections(
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int
    ): List<CollectionDto>

    @GET("/users/{userName}/collections")
    suspend fun getUserCollection(
        @Path("userName") userName: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int
    ): List<CollectionDto>
}