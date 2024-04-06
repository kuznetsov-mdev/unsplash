package com.skillbox.unsplash.domain.model.local

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingScreenModel(
    @DrawableRes val drawableRes: Int,
    @StringRes val textRes: Int
)