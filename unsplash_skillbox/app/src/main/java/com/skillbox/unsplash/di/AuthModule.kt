package com.skillbox.unsplash.di

import android.app.Application
import com.skillbox.unsplash.data.remote.retrofit.AuthRepositoryApi
import com.skillbox.unsplash.data.repository.AuthRepositoryImpl
import com.skillbox.unsplash.domain.api.service.AuthServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    @Singleton
    fun providesAuthorizationService(context: Application): AuthorizationService =
        AuthorizationService(context)

    @Provides
    @Singleton
    fun providesAuthRepository(authService: AuthServiceApi): AuthRepositoryApi =
        AuthRepositoryImpl(authService)

}