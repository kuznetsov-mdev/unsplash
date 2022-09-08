package com.skillbox.unsplash.onboarding.repository

import android.content.Context
import com.skillbox.unsplash.R
import com.skillbox.unsplash.onboarding.api.OnboardingRepositoryApi
import com.skillbox.unsplash.onboarding.data.OnBoardingScreen
import com.skillbox.unsplash.onboarding.repository.OnBoardingRepositoryImpl.SharedPrefs.ONBOARDING_COMPLETED_KEY
import com.skillbox.unsplash.onboarding.repository.OnBoardingRepositoryImpl.SharedPrefs.SHARED_PREFS_NAME

class OnBoardingRepositoryImpl : OnboardingRepositoryApi {
    private val onboardingScreens: List<OnBoardingScreen> = listOf(
        OnBoardingScreen(R.drawable.on_boarding_bkg, R.string.intro_first),
        OnBoardingScreen(R.drawable.on_boarding_bkg, R.string.intro_second),
        OnBoardingScreen(R.drawable.on_boarding_bkg, R.string.intro_third)
    )

    override fun getScreens(): List<OnBoardingScreen> {
        return onboardingScreens
    }

    override suspend fun isOnboardingCompleted(context: Context): Boolean {
        return context
            .getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(ONBOARDING_COMPLETED_KEY, false)
    }

    override suspend fun setOnboardingCompletedStatus(context: Context, isCompleted: Boolean) {
        context
            .getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(ONBOARDING_COMPLETED_KEY, isCompleted)
            .apply()
    }

    object SharedPrefs {
        const val SHARED_PREFS_NAME = "unsplash_shared_prefs"
        const val ONBOARDING_COMPLETED_KEY = "onboarding_completed_key"
    }

}