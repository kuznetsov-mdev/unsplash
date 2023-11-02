package com.skillbox.unsplash.data.onboarding

import android.content.Context
import com.skillbox.unsplash.data.onboarding.model.OnBoardingScreenModel

interface OnBoardingRepositoryApi {

    fun getScreens(): List<OnBoardingScreenModel>

    suspend fun isOnBoardingCompleted(context: Context): Boolean

    suspend fun setOnBoardingCompletedStatus(context: Context, isCompleted: Boolean)
}