package com.skillbox.unsplash.data.onboarding

import android.content.Context
import com.skillbox.unsplash.R
import com.skillbox.unsplash.data.onboarding.OnBoardingRepositoryImpl.SharedPrefs.ON_BOARDING_COMPLETED_KEY
import com.skillbox.unsplash.data.onboarding.OnBoardingRepositoryImpl.SharedPrefs.SHARED_PREFS_NAME
import com.skillbox.unsplash.data.onboarding.model.OnBoardingScreen
import javax.inject.Inject

class OnBoardingRepositoryImpl @Inject constructor() : OnBoardingRepositoryApi {
    private val onBoardingScreens: List<OnBoardingScreen> = listOf(
        OnBoardingScreen(R.drawable.on_boarding_bkg, R.string.intro_first),
        OnBoardingScreen(R.drawable.on_boarding_bkg, R.string.intro_second),
        OnBoardingScreen(R.drawable.on_boarding_bkg, R.string.intro_third)
    )

    override fun getScreens(): List<OnBoardingScreen> {
        return onBoardingScreens
    }

    override suspend fun isOnBoardingCompleted(context: Context): Boolean {
        return context
            .getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            .getBoolean(ON_BOARDING_COMPLETED_KEY, false)
    }

    override suspend fun setOnBoardingCompletedStatus(context: Context, isCompleted: Boolean) {
        context
            .getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
            .edit()
            .putBoolean(ON_BOARDING_COMPLETED_KEY, isCompleted)
            .apply()
    }

    object SharedPrefs {
        const val SHARED_PREFS_NAME = "unsplash_shared_prefs"
        const val ON_BOARDING_COMPLETED_KEY = "on_boarding_completed_key"
    }

}