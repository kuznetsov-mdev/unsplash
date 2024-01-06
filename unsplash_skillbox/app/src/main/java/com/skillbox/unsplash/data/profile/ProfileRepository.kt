package com.skillbox.unsplash.data.profile

import com.skillbox.unsplash.common.extensions.toUiModel
import com.skillbox.unsplash.data.common.retrofit.UnsplashResponse
import com.skillbox.unsplash.data.profile.retrofit.RetrofitProfileRepositoryApi
import com.skillbox.unsplash.feature.profile.model.ProfileUiModel
import com.skillbox.unsplash.feature.profile.model.ResponseResult
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val retrofitAccountRepo: RetrofitProfileRepositoryApi
) {
    suspend fun getInfo(): ResponseResult<ProfileUiModel> {
        return when (val result = retrofitAccountRepo.getInfo()) {
            is UnsplashResponse.Success -> ResponseResult.Content(result.data.toUiModel())
            is UnsplashResponse.Error -> ResponseResult.Error(result.throwable)
        }
    }
}