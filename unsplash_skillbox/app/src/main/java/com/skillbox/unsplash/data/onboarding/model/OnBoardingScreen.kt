package com.skillbox.unsplash.data.onboarding.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingScreen(
    @DrawableRes val drawableRes: Int,
    @StringRes val textRes: Int
)