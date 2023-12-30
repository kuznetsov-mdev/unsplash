package com.skillbox.unsplash.data.profile.retrofit

import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.common.retrofit.UnsplashResult
import com.skillbox.unsplash.data.profile.retrofit.model.UserProfileDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RetrofitProfileRepositoryImpl(
    private val network: Network
) : RetrofitProfileRepositoryApi {
    override suspend fun getInfo(): UnsplashResult<UserProfileDto> {
        return withContext(Dispatchers.IO) {
            try {
                UnsplashResult.Success(network.profileApi.getInfo())
            } catch (e: HttpException) {
                e.printStackTrace()
                UnsplashResult.Error(e)
            }
        }
    }
}