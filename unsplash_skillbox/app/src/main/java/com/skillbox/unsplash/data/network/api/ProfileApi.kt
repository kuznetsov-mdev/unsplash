package com.skillbox.unsplash.data.network.api

import com.skillbox.unsplash.domain.model.remote.UserProfileDto
import retrofit2.http.GET

interface ProfileApi {

    @GET("/me")
    suspend fun getInfo(): UserProfileDto
}