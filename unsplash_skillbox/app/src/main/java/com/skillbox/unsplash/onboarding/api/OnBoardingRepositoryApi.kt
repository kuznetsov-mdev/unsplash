package com.skillbox.unsplash.onboarding.api

import android.content.Context
import com.skillbox.unsplash.onboarding.data.OnBoardingScreen

interface OnBoardingRepositoryApi {

    fun getScreens(): List<OnBoardingScreen>

    suspend fun isOnBoardingCompleted(context: Context): Boolean

    suspend fun setOnBoardingCompletedStatus(context: Context, isCompleted: Boolean)
}