package com.skillbox.unsplash.domain.api.repository

import com.skillbox.unsplash.common.UnsplashResponse
import com.skillbox.unsplash.data.remote.dto.user.UserProfileDto

interface RetrofitProfileRepositoryApi {

    suspend fun getInfo(): UnsplashResponse<UserProfileDto>
}