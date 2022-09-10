package com.skillbox.unsplash.onboarding.di

import com.skillbox.unsplash.onboarding.api.OnboardingRepositoryApi
import com.skillbox.unsplash.onboarding.repository.OnBoardingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class OnboardingRepositoryModule {

    @Provides
    fun providesOnboardingRepository(): OnboardingRepositoryApi  {
        return OnBoardingRepositoryImpl()
    }
}