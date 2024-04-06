package com.skillbox.unsplash.presentation.onboarding

import android.content.Context
import androidx.lifecycle.ViewModel
import com.skillbox.unsplash.data.remote.retrofit.AuthRepositoryApi
import com.skillbox.unsplash.data.remote.retrofit.OnBoardingRepositoryApi
import com.skillbox.unsplash.domain.model.local.OnBoardingScreenModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val repository: OnBoardingRepositoryApi,
    private val authRepository: AuthRepositoryApi
) : ViewModel() {

    suspend fun isOnBoardingCompleted(context: Context): Boolean {
        return repository.isOnBoardingCompleted(context)
    }

    fun getScreens(): List<OnBoardingScreenModel> {
        return repository.getScreens()
    }

    suspend fun setOnBoardingCompletedStatus(context: Context, isCompleted: Boolean) {
        repository.setOnBoardingCompletedStatus(context, isCompleted)
    }

    fun isUserLoggedIn(): Boolean {
        return authRepository.isUserLoggedIn()
    }


}