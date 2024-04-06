package com.skillbox.unsplash.data.remote.retrofit

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Url

interface UploaderApi {
    @GET
    suspend fun getFile(
        @Url url: String
    ): ResponseBody
}