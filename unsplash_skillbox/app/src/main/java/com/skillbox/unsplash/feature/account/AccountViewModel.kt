package com.skillbox.unsplash.feature.account

import androidx.lifecycle.ViewModel
import com.skillbox.unsplash.common.network.ConnectivityStatus
import com.skillbox.unsplash.common.network.api.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = connectivityObserver.observe()

    fun getAccountInfo() {

    }

}