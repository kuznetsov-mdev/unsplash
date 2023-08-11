package com.skillbox.unsplash.common.network.api

import com.skillbox.unsplash.data.images.retrofit.model.RemoteImage
import okhttp3.ResponseBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {
    @GET("photos")
    suspend fun searchImages(
        @Query("page") pageNumber: Int,
        @Query("per_page") pageSize: Int
    ): List<RemoteImage>

    @POST("/photos/{id}/like")
    suspend fun setLike(
        @Path("id") photoId: String
    ): ResponseBody

    @DELETE("/photos/{id}/like")
    suspend fun removeLike(
        @Path("id") photoId: String
    ): ResponseBody
}