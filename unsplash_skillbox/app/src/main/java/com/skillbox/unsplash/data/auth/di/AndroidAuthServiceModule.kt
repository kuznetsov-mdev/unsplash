package com.skillbox.unsplash.data.auth.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationService

@Module
@InstallIn(SingletonComponent::class)
class AndroidAuthServiceModule {

    @Provides
    fun providesAndroidAuthService(application: Application): AuthorizationService {
        return AuthorizationService(application)
    }
}