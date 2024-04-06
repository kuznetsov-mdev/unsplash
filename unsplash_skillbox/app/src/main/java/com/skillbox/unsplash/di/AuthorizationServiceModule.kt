package com.skillbox.unsplash.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import net.openid.appauth.AuthorizationService

@Module
@InstallIn(ViewModelComponent::class)
class AuthorizationServiceModule {

    @Provides
    @ViewModelScoped
    fun providesAuthorizationService(context: Application): AuthorizationService {
        return AuthorizationService(context)
    }
}