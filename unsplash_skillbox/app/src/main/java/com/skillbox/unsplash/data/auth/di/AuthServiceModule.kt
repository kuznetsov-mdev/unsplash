package com.skillbox.unsplash.data.auth.di

import com.skillbox.unsplash.data.auth.service.AuthServiceApi
import com.skillbox.unsplash.data.auth.service.AuthServiceImpl
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