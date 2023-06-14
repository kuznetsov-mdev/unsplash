package com.skillbox.unsplash.feature.auth

import android.app.Application
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.skillbox.unsplash.R
import com.skillbox.unsplash.data.auth.AppAuth
import com.skillbox.unsplash.data.auth.repository.AuthRepositoryApi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import net.openid.appauth.AuthorizationException
import net.openid.appauth.AuthorizationResponse
import net.openid.appauth.AuthorizationService
import net.openid.appauth.TokenRequest
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    application: Application,
    private val repository: AuthRepositoryApi,
    private val authService: AuthorizationService
) : AndroidViewModel(application) {
    private val openAuthPageEventChannel = Channel<Intent>(Channel.BUFFERED)
    private val toastEventChannel = Channel<Int>(Channel.BUFFERED)
    private val authSuccessEventChannel = Channel<Unit>(Channel.BUFFERED)
    private val loadingMutableStateFlow = MutableStateFlow(false)

    val openAuthPageFlow: Flow<Intent>
        get() = openAuthPageEventChannel.receiveAsFlow()
    val toastFlow: Flow<Int>
        get() = toastEventChannel.receiveAsFlow()
    val authSuccessFlow: Flow<Unit>
        get() = authSuccessEventChannel.receiveAsFlow()
    val loadingFlow: Flow<Boolean>
        get() = loadingMutableStateFlow.asStateFlow()

    override fun onCleared() {
        super.onCleared()
        authService.dispose()
    }

    fun openLoginPage() {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        val authRequest = AppAuth.getAuthRequest()

        Timber.tag("Oauth").d(
            "1. Generated verifier=${authRequest.codeVerifier}," +
                    "challenge=${authRequest.codeVerifierChallenge}"
        )

        val openAuthPageIntent = authService.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent
        )

        openAuthPageEventChannel.trySendBlocking(openAuthPageIntent)
        Timber.tag("Oauth").d("2. Open auth page: ${authRequest.toUri()}")
    }

    fun onAuthCodeFailed(exception: AuthorizationException) {
        toastEventChannel.trySendBlocking(R.string.auth_canceled)
        Timber.tag("Oauth").d("cause is ${exception.error}")
    }

    fun onAuthCodeReceived(tokenRequest: TokenRequest) {
        //Процедура обмена кода на токен
        Timber.tag("Oauth").d("3. Received code = ${tokenRequest.authorizationCode}")

        viewModelScope.launch {
            loadingMutableStateFlow.value = true
            runCatching {
                Timber.tag("Oauth")
                    .d("4. Change code to token. Url = ${tokenRequest.configuration.tokenEndpoint}, verifier = ${tokenRequest.codeVerifier}")
                repository.performTokenRequest(
                    authService = authService,
                    tokenRequest = tokenRequest
                )
            }.onSuccess {
                loadingMutableStateFlow.value = false
                authSuccessEventChannel.send(Unit)
            }.onFailure {
                loadingMutableStateFlow.value = false
                toastEventChannel.send(R.string.auth_canceled)
            }
        }
    }

    fun handleAuthResponseIntent(intent: Intent) {
        //пытаемся получить ошибку из ответа. null - есди все ок
        val exception = AuthorizationException.fromIntent(intent)
        //пытаемся получить запрос для обмена кода на токен, null - если произошла ошибка
        val tokenExchangeRequest = AuthorizationResponse.fromIntent(intent)?.createTokenExchangeRequest()
        when {
            //авторизация завершилась ошибкой
            exception != null -> onAuthCodeFailed(exception)
            //авторизация прошла успешно
            tokenExchangeRequest != null -> onAuthCodeReceived(tokenExchangeRequest)
        }
    }
}