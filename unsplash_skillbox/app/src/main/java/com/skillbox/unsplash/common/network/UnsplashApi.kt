package com.skillbox.unsplash.common.network

import com.skillbox.unsplash.data.images.model.RemoteImage
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface UnsplashApi {
    @GET("photos")
    fun searchImages(
        @Query("per_page") imageCount: Int
    ): Call<List<RemoteImage>>
}