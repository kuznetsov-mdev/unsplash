package com.skillbox.unsplash.onboarding.viewmodel

import androidx.lifecycle.ViewModel
import com.skillbox.unsplash.onboarding.api.OnboardingRepositoryApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val repository: OnboardingRepositoryApi
) : ViewModel() {


}