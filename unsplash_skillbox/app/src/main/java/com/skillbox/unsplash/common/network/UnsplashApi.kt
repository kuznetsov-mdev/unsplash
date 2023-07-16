package com.skillbox.unsplash.common.network

import com.skillbox.unsplash.data.images.model.RemoteImage
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface UnsplashApi {
    @GET("photos")
    fun searchImages(
        @Query("per_page") imageCount: Int
    ): Call<List<RemoteImage>>

    @POST("/photos/{id}/like")
    fun setLike(
        @Path("id") photoId: String
    ): Call<ResponseBody>

    @DELETE("/photos/{id}/like")
    fun removeLike(
        @Path("id") photoId: String
    ): Call<ResponseBody>
}