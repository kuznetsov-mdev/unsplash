package com.skillbox.unsplash.di

import com.skillbox.unsplash.data.service.AuthServiceImpl
import com.skillbox.unsplash.domain.api.service.AuthServiceApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthServiceModule {

    @Binds
    fun providesAuthNetwork(network: AuthServiceImpl): AuthServiceApi
}