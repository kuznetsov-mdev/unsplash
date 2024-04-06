package com.skillbox.unsplash.di

import com.skillbox.unsplash.data.impl.RetrofitProfileRepositoryImpl
import com.skillbox.unsplash.data.remote.network.Network
import com.skillbox.unsplash.domain.api.repository.RetrofitProfileRepositoryApi
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