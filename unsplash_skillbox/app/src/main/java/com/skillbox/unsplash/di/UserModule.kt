package com.skillbox.unsplash.di

import com.skillbox.unsplash.data.remote.network.Network
import com.skillbox.unsplash.data.repository.ProfileRepositoryImpl
import com.skillbox.unsplash.domain.api.repository.ProfileRepositoryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UserModule {

    @Provides
    @Singleton
    fun providesRetrofitAccountRepository(network: Network): ProfileRepositoryApi =
        ProfileRepositoryImpl(network)
}