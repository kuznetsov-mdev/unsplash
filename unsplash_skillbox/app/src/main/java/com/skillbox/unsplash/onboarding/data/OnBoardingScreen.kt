package com.skillbox.unsplash.onboarding.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingScreen(
    @DrawableRes val drawableRes: Int,
    @StringRes val textRes: Int
)