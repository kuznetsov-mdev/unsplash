package com.skillbox.unsplash.common.network.api

import com.skillbox.unsplash.data.profile.retrofit.model.UserProfileDto
import retrofit2.http.GET

interface ProfileApi {

    @GET("/me")
    suspend fun getInfo(): UserProfileDto
}