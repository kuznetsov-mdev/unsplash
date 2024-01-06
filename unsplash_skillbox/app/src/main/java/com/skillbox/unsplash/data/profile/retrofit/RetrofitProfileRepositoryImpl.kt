package com.skillbox.unsplash.data.profile.retrofit

import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.common.retrofit.UnsplashResponse
import com.skillbox.unsplash.data.profile.retrofit.model.UserProfileDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RetrofitProfileRepositoryImpl(
    private val network: Network
) : RetrofitProfileRepositoryApi {
    override suspend fun getInfo(): UnsplashResponse<UserProfileDto> {
        return withContext(Dispatchers.IO) {
            try {
                UnsplashResponse.Success(network.profileApi.getInfo())
            } catch (e: HttpException) {
                e.printStackTrace()
                UnsplashResponse.Error(e)
            }
        }
    }
}