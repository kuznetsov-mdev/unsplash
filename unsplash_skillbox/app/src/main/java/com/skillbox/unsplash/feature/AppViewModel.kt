package com.skillbox.unsplash.feature

import androidx.lifecycle.ViewModel
import com.skillbox.unsplash.common.network.api.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    val connectivityObserver: ConnectivityObserver
) : ViewModel()