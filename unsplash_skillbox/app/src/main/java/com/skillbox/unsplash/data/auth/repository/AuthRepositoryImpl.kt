package com.skillbox.unsplash.data.auth.repository

import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import com.skillbox.unsplash.data.auth.AppAuth
import com.skillbox.unsplash.data.auth.model.TokenStorage
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.TokenRequest
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthorizationService
) : AuthRepositoryApi {

    override fun corruptAccessToken() {
        TokenStorage.accessToken = "fake token"
    }

    override fun logout() {
        TokenStorage.accessToken = null
        TokenStorage.refreshToken = null
        TokenStorage.idToken = null
    }

    override fun getAuthRequest(): AuthorizationRequest {
        return AppAuth.getAuthRequest()
    }

    override fun getEndSessionRequest(): EndSessionRequest {
        return AppAuth.getEndSessionRequest()
    }

    override suspend fun performTokenRequest(tokenRequest: TokenRequest) {
        val tokens = AppAuth.performTokenRequestSuspend(authService, tokenRequest)
        //обмен кода на токен произошел успешно, сохраняем токены и завершаем авторизацию
        TokenStorage.accessToken = tokens.accessToken
        TokenStorage.refreshToken = tokens.refreshToken
        TokenStorage.idToken = tokens.idToken
        Timber.tag("Oauth")
            .d("6. Tokens accepted:\n access=${tokens.accessToken}\nrefresh=${tokens.refreshToken}\nidToken=${tokens.idToken}")
    }

    override fun getAuthorizationRequestIntent(
        authRequest: AuthorizationRequest,
        customTabsIntent: CustomTabsIntent
    ): Intent {
        return authService.getAuthorizationRequestIntent(
            authRequest,
            customTabsIntent
        )
    }
}