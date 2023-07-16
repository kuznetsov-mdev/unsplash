package com.skillbox.unsplash.common.network

import com.skillbox.unsplash.data.images.model.RemoteImage
import retrofit2.Call
import retrofit2.http.GET

interface UnsplashApi {
    @GET("photos")
    fun searchImages(): Call<List<RemoteImage>>
}