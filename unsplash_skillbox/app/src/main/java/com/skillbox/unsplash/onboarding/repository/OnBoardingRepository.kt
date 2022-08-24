package com.skillbox.unsplash.onboarding.repository

import com.skillbox.unsplash.R
import com.skillbox.unsplash.onboarding.data.OnBoardingScreen

class OnBoardingRepository {
    private val onboardingScreens: List<OnBoardingScreen> = listOf(
        OnBoardingScreen(R.drawable.on_boarding_bkg, R.string.intro_first),
        OnBoardingScreen(R.drawable.on_boarding_bkg, R.string.intro_second),
        OnBoardingScreen(R.drawable.on_boarding_bkg, R.string.intro_third)
    )

    fun getScreens(): List<OnBoardingScreen> {
        return onboardingScreens
    }
}