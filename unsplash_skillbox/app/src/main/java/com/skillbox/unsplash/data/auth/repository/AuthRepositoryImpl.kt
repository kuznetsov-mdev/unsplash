package com.skillbox.unsplash.data.auth.repository

import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import com.skillbox.unsplash.data.auth.model.TokenStorageDataModel
import com.skillbox.unsplash.data.auth.service.AuthServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.TokenRequest
import timber.log.Timber
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthServiceApi
) : AuthRepositoryApi {

    override fun corruptAccessToken() {
        TokenStorageDataModel.accessToken = "fake token"
    }

    override fun logout() {
        TokenStorageDataModel.accessToken = null
        TokenStorageDataModel.refreshToken = null
        TokenStorageDataModel.idToken = null
    }

    override fun getAuthRequest(): AuthorizationRequest {
        return authService.getAuthRequest()
    }

    override fun getEndSessionRequest(): EndSessionRequest {
        return authService.getEndSessionRequest()
    }

    override suspend fun performTokenRequest(tokenRequest: TokenRequest) {
        withContext(Dispatchers.IO) {
            val tokens = authService.performTokenRequestSuspend(tokenRequest)
            TokenStorageDataModel.accessToken = tokens.accessToken
            TokenStorageDataModel.refreshToken = tokens.refreshToken
            TokenStorageDataModel.idToken = tokens.idToken
            Timber.tag("Oauth")
                .d("5. Tokens accepted:\n access=${tokens.accessToken}\nrefresh=${tokens.refreshToken}\nidToken=${tokens.idToken}")
        }
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

    override fun isUserLoggedIn(): Boolean {
        return with(TokenStorageDataModel) {
            accessToken != null && refreshToken != null && idToken != null
        }
    }

    override fun disposeAuthService() {
        authService.disposeAuthService()
    }
}