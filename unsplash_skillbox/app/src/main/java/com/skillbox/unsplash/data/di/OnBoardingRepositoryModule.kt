package com.skillbox.unsplash.data.di

import com.skillbox.unsplash.data.impl.OnBoardingRepositoryImpl
import com.skillbox.unsplash.data.remote.retrofit.OnBoardingRepositoryApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
interface OnBoardingRepositoryModule {

    @Binds
    @ViewModelScoped
    fun providesOnBoardingRepository(
        repository: OnBoardingRepositoryImpl
    ): OnBoardingRepositoryApi
}