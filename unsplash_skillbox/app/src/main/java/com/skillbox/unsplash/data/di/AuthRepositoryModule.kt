package com.skillbox.unsplash.data.di

import com.skillbox.unsplash.data.impl.AuthRepositoryImpl
import com.skillbox.unsplash.data.remote.retrofit.AuthRepositoryApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface AuthRepositoryModule {

    @Binds
    @ViewModelScoped
    fun bindAuthRepository(
        repository: AuthRepositoryImpl
    ): AuthRepositoryApi
}