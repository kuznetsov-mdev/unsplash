package com.skillbox.unsplash.data.auth.repository

import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.TokenRequest

interface AuthRepositoryApi {

    fun corruptAccessToken()

    fun logout()

    fun getAuthRequest(): AuthorizationRequest

    fun getEndSessionRequest(): EndSessionRequest

    suspend fun performTokenRequest(tokenRequest: TokenRequest)

    fun getAuthorizationRequestIntent(authRequest: AuthorizationRequest, customTabsIntent: CustomTabsIntent): Intent

    fun isUserLoggedIn(): Boolean

    fun disposeAuthService()
}