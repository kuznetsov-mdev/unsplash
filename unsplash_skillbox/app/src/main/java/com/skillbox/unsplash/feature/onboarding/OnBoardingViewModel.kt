package com.skillbox.unsplash.feature.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import com.skillbox.unsplash.data.onboarding.OnBoardingRepositoryApi
import com.skillbox.unsplash.data.onboarding.model.OnBoardingScreen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val repository: OnBoardingRepositoryApi
) : ViewModel() {

    suspend fun isOnBoardingCompleted(context: Context): Boolean {
        return repository.isOnBoardingCompleted(context)
    }

    fun getScreens(): List<OnBoardingScreen> {
        return repository.getScreens()
    }

    suspend fun setOnBoardingCompletedStatus(context: Context, isCompleted: Boolean) {
        repository.setOnBoardingCompletedStatus(context, isCompleted)
    }


}