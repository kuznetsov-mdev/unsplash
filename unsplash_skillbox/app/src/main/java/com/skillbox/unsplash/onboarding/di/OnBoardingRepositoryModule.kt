package com.skillbox.unsplash.onboarding.di

import com.skillbox.unsplash.onboarding.api.OnBoardingRepositoryApi
import com.skillbox.unsplash.onboarding.repository.OnBoardingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class OnBoardingRepositoryModule {

    @Provides
    fun providesOnBoardingRepository(): OnBoardingRepositoryApi {
        return OnBoardingRepositoryImpl()
    }
}