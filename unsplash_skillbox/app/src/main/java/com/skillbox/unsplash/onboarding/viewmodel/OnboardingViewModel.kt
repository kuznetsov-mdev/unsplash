package com.skillbox.unsplash.onboarding.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.skillbox.unsplash.onboarding.api.OnboardingRepositoryApi
import com.skillbox.unsplash.onboarding.data.OnBoardingScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: OnboardingRepositoryApi
) : ViewModel() {

    suspend fun isOnboardingCompleted(context: Context): Boolean {
        return repository.isOnboardingCompleted(context)
    }

    fun getScreens(): List<OnBoardingScreen> {
        return repository.getScreens()
    }

    suspend fun setOnboardingCompletedStatus(context: Context, isCompleted: Boolean) {
        repository.setOnboardingCompletedStatus(context, isCompleted)
    }


}