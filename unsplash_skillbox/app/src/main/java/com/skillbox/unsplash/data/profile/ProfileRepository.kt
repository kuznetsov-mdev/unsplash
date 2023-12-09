package com.skillbox.unsplash.data.profile

import com.skillbox.unsplash.common.extensions.toUiModel
import com.skillbox.unsplash.data.profile.retrofit.RetrofitProfileRepositoryApi
import com.skillbox.unsplash.feature.profile.model.ProfileUiModel
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val retrofitAccountRepo: RetrofitProfileRepositoryApi
) {

    suspend fun getInfo(): ProfileUiModel {
        return retrofitAccountRepo.getInfo().toUiModel()
    }

}