package com.skillbox.unsplash.data.profile.retrofit

import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.profile.retrofit.model.UserProfileDto

class RetrofitProfileRepositoryImpl(
    private val network: Network
) : RetrofitProfileRepositoryApi {
    override suspend fun getInfo(): UserProfileDto {
        return network.profileApi.getInfo();
    }

}