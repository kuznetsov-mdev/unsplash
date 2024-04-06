package com.skillbox.unsplash.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface AuthRepositoryModule {

//    @Binds
//    @ViewModelScoped
//    fun bindAuthRepository(
//        repository: AuthRepositoryImpl
//    ): AuthRepositoryApi
}