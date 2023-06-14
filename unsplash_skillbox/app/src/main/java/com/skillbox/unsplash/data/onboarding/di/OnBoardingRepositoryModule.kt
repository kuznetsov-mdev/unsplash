package com.skillbox.unsplash.data.onboarding.di

import com.skillbox.unsplash.data.onboarding.OnBoardingRepositoryApi
import com.skillbox.unsplash.data.onboarding.OnBoardingRepositoryImpl
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