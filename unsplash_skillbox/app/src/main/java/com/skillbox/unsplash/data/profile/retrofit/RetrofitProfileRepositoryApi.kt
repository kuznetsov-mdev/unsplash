package com.skillbox.unsplash.data.profile.retrofit

import com.skillbox.unsplash.data.common.retrofit.UnsplashResponse
import com.skillbox.unsplash.data.profile.retrofit.model.UserProfileDto

interface RetrofitProfileRepositoryApi {

    suspend fun getInfo(): UnsplashResponse<UserProfileDto>
}