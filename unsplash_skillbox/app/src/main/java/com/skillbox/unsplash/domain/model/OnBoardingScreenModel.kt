package com.skillbox.unsplash.domain.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingScreenModel(
    @DrawableRes val drawableRes: Int,
    @StringRes val textRes: Int
)