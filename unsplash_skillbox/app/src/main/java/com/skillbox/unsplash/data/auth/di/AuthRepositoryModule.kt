package com.skillbox.unsplash.data.auth.di

import com.skillbox.unsplash.data.auth.repository.AuthRepositoryApi
import com.skillbox.unsplash.data.auth.repository.AuthRepositoryImpl
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