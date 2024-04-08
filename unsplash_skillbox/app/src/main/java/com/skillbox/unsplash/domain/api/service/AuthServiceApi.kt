package com.skillbox.unsplash.domain.api.service

import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import com.skillbox.unsplash.data.service.model.TokensModel
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.TokenRequest

interface AuthServiceApi {

    fun getAuthRequest(): AuthorizationRequest

    fun getEndSessionRequest(): EndSessionRequest

    fun getRefreshTokenRequest(refreshToken: String): TokenRequest

    suspend fun performTokenRequestSuspend(tokenRequest: TokenRequest): TokensModel

    fun getAuthorizationRequestIntent(
        authRequest: AuthorizationRequest,
        customTabsIntent: CustomTabsIntent
    ): Intent

    fun disposeAuthService()

    fun getEndSessionRequestIntent(customTabsIntent: CustomTabsIntent): Intent

}