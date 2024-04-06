package com.skillbox.unsplash.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class AuthorizationServiceModule {
//
//    @Provides
//    @ViewModelScoped
//    fun providesAuthorizationService(context: Application): AuthorizationService {
//        return AuthorizationService(context)
//    }
}