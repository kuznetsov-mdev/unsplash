package com.skillbox.unsplash.data.profile.di

import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.profile.retrofit.RetrofitProfileRepositoryApi
import com.skillbox.unsplash.data.profile.retrofit.RetrofitProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProfileDataModule {

    @Provides
    @Singleton
    fun providesRetrofitAccountRepository(network: Network): RetrofitProfileRepositoryApi =
        RetrofitProfileRepositoryImpl(network)
}