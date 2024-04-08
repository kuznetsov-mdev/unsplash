package com.skillbox.unsplash.data.remote.retrofit

import com.skillbox.unsplash.data.remote.dto.user.UserProfileDto
import retrofit2.http.GET

interface ProfileApi {

    @GET("/me")
    suspend fun getInfo(): UserProfileDto
}