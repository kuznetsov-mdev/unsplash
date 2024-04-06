package com.skillbox.unsplash.data.repository

import com.skillbox.unsplash.common.UnsplashResponse
import com.skillbox.unsplash.common.extensions.toUiModel
import com.skillbox.unsplash.domain.api.repository.RetrofitProfileRepositoryApi
import com.skillbox.unsplash.domain.model.ProfileModel
import com.skillbox.unsplash.domain.model.ResponseResultType
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val retrofitAccountRepo: RetrofitProfileRepositoryApi
) {
    suspend fun getInfo(): ResponseResultType<ProfileModel> {
        return when (val result = retrofitAccountRepo.getInfo()) {
            is UnsplashResponse.Success -> ResponseResultType.Success(result.data.toUiModel())
            is UnsplashResponse.Error -> ResponseResultType.Error(result.throwable)
        }
    }
}