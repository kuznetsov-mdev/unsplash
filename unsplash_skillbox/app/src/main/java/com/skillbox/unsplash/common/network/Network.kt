package com.skillbox.unsplash.common.network

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.skillbox.unsplash.common.network.api.CollectionsApi
import com.skillbox.unsplash.common.network.api.ImagesApi
import com.skillbox.unsplash.common.network.api.UploaderApi
import com.skillbox.unsplash.data.auth.model.TokenStorageDataModel
import com.skillbox.unsplash.data.auth.service.AuthServiceApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Inject

class Network @Inject constructor(
    authServiceApi: AuthServiceApi
) {
    private val okHttpClient = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addNetworkInterceptor(AuthInterceptor())
        .addNetworkInterceptor(AuthFailedInterceptor(TokenStorageDataModel, authServiceApi))
        .addNetworkInterceptor(FlipperOkhttpInterceptor(NETWORK_FLIPPER_PLUGIN))
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.unsplash.com/")
        .addConverterFactory(MoshiConverterFactory.create())
        .client(okHttpClient)
        .build()

    val imagesApi: ImagesApi
        get() = retrofit.create()

    val collectionsApi: CollectionsApi
        get() = retrofit.create()

    val uploaderApi: UploaderApi
        get() = retrofit.create()

    companion object {
        val NETWORK_FLIPPER_PLUGIN = NetworkFlipperPlugin()
    }
}