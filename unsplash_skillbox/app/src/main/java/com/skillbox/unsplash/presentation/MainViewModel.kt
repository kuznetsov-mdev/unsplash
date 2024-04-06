package com.skillbox.unsplash.presentation

import android.app.Activity
import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.unsplash.data.remote.network.ConnectivityObserver
import com.skillbox.unsplash.data.remote.network.ConnectivityStatus
import com.skillbox.unsplash.data.remote.retrofit.AppRepositoryApi
import com.skillbox.unsplash.data.remote.retrofit.AuthRepositoryApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val connectivityObserver: ConnectivityObserver,
    private val authRepositoryApi: AuthRepositoryApi,
    private val appRepository: AppRepositoryApi
) : ViewModel() {

    private val logoutCompletedEventChannel = Channel<Unit>(Channel.BUFFERED)
    private val logoutPageEventChannel = Channel<Intent>(Channel.BUFFERED)

    val logoutCompletedFlow: Flow<Unit>
        get() = logoutCompletedEventChannel.receiveAsFlow()

    val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = connectivityObserver.observe()

    val logoutPageFlow: Flow<Intent>
        get() = logoutPageEventChannel.receiveAsFlow()

    fun logout() {
        clearAllData()
        val customTabsIntent = CustomTabsIntent.Builder().build()
        val logoutPageIntent = authRepositoryApi.getEndSessionRequestIntent(customTabsIntent)
        logoutPageEventChannel.trySendBlocking(logoutPageIntent)
        authRepositoryApi.logout()
        logoutCompletedEventChannel.trySendBlocking(Unit)
    }

    private fun clearAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            appRepository.clearAllData()
        }
    }

    fun webLogoutComplete(logoutResult: ActivityResult) {
        if (logoutResult.resultCode == Activity.RESULT_OK) {
            logoutCompletedEventChannel.trySendBlocking(Unit)
        }
    }
}