package com.skillbox.unsplash.data.onboarding

import android.content.Context
import com.skillbox.unsplash.data.onboarding.model.OnBoardingScreen

interface OnBoardingRepositoryApi {

    fun getScreens(): List<OnBoardingScreen>

    suspend fun isOnBoardingCompleted(context: Context): Boolean

    suspend fun setOnBoardingCompletedStatus(context: Context, isCompleted: Boolean)
}