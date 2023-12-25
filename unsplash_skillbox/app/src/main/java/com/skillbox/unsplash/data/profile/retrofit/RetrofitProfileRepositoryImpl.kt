package com.skillbox.unsplash.data.profile.retrofit

import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.profile.retrofit.model.UserProfileDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RetrofitProfileRepositoryImpl(
    private val network: Network
) : RetrofitProfileRepositoryApi {
    override suspend fun getInfo(): UserProfileDto {
        return withContext(Dispatchers.IO) {
            try {
                network.profileApi.getInfo()
            } catch (e: HttpException) {
                e.printStackTrace()
                throw Error(e)
            }
        }
    }

}