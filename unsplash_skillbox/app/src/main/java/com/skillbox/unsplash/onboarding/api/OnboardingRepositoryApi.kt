package com.skillbox.unsplash.onboarding.api

import android.content.Context
import com.skillbox.unsplash.onboarding.data.OnBoardingScreen

interface OnboardingRepositoryApi {

    fun getScreens(): List<OnBoardingScreen>

    suspend fun isOnboardingCompleted(context: Context): Boolean

    suspend fun setOnboardingCompletedStatus(context: Context, isCompleted: Boolean)
}