package com.skillbox.unsplash.domain.usecase.profile

import com.skillbox.unsplash.common.UnsplashResponse
import com.skillbox.unsplash.common.extensions.toUiModel
import com.skillbox.unsplash.domain.api.repository.ProfileRepositoryApi
import com.skillbox.unsplash.domain.model.ProfileModel
import com.skillbox.unsplash.domain.model.ResponseResultType
import javax.inject.Inject

class GetUserProfileUseCase @Inject constructor(
    private val retrofitAccountRepo: ProfileRepositoryApi
) {
    suspend operator fun invoke(): ResponseResultType<ProfileModel> {
        return when (val result = retrofitAccountRepo.getInfo()) {
            is UnsplashResponse.Success -> ResponseResultType.Success(result.data.toUiModel())
            is UnsplashResponse.Error -> ResponseResultType.Error(result.throwable)
        }
    }
}