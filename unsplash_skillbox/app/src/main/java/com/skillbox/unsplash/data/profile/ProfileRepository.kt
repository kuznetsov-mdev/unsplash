package com.skillbox.unsplash.data.profile

import com.skillbox.unsplash.common.extensions.toUiModel
import com.skillbox.unsplash.data.common.retrofit.UnsplashResponse
import com.skillbox.unsplash.data.profile.retrofit.RetrofitProfileRepositoryApi
import com.skillbox.unsplash.domain.model.local.ProfileUiModel
import com.skillbox.unsplash.domain.model.local.ResponseResultType
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val retrofitAccountRepo: RetrofitProfileRepositoryApi
) {
    suspend fun getInfo(): ResponseResultType<ProfileUiModel> {
        return when (val result = retrofitAccountRepo.getInfo()) {
            is UnsplashResponse.Success -> ResponseResultType.Success(result.data.toUiModel())
            is UnsplashResponse.Error -> ResponseResultType.Error(result.throwable)
        }
    }
}