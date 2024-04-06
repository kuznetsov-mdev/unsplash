package com.skillbox.unsplash.data.repository

import com.skillbox.unsplash.common.UnsplashResponse
import com.skillbox.unsplash.data.remote.dto.user.UserProfileDto
import com.skillbox.unsplash.data.remote.network.Network
import com.skillbox.unsplash.domain.api.repository.RetrofitProfileRepositoryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class RetrofitProfileRepositoryImpl @Inject constructor(
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