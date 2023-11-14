package com.skillbox.unsplash.common.network.api

import com.skillbox.unsplash.data.collections.retrofit.model.CollectionDto
import com.skillbox.unsplash.data.images.retrofit.model.ImageRetrofitModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CollectionsApi {

    @GET("collections")
    suspend fun getCollections(
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int
    ): List<CollectionDto>

    @GET("/collections/{id}/photos")
    suspend fun getCollectionImages(
        @Path("id") collectionId: String,
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int
    ): List<ImageRetrofitModel>
}