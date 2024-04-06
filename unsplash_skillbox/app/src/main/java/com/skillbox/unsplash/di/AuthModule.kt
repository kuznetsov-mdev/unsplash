package com.skillbox.unsplash.di

import android.app.Application
import com.skillbox.unsplash.data.remote.retrofit.AuthRepositoryApi
import com.skillbox.unsplash.data.repository.AuthRepositoryImpl
import com.skillbox.unsplash.domain.api.service.AuthServiceApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import net.openid.appauth.AuthorizationService

@Module
@InstallIn(ViewModelComponent::class)
class AuthModule {

    @Provides
    @ViewModelScoped
    fun providesAuthorizationService(context: Application): AuthorizationService =
        AuthorizationService(context)

//    @Provides
//    @ViewModelScoped
//    fun providesAuthService(authorizationService: AuthorizationService): AuthServiceApi =
//        AuthServiceImpl(authorizationService)

    @Provides
    @ViewModelScoped
    fun providesAuthRepository(authService: AuthServiceApi): AuthRepositoryApi =
        AuthRepositoryImpl(authService)
}