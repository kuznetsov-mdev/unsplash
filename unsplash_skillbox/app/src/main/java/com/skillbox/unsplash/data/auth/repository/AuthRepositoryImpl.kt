package com.skillbox.unsplash.data.auth.repository

import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import com.skillbox.unsplash.data.auth.AuthConfig
import com.skillbox.unsplash.data.auth.model.TokenStorage
import com.skillbox.unsplash.data.auth.service.AuthServiceApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.TokenRequest
import timber.log.Timber
import java.net.HttpURLConnection
import java.net.URL
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authService: AuthServiceApi
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
        return authService.getAuthRequest()
    }

    override fun getEndSessionRequest(): EndSessionRequest {
        return authService.getEndSessionRequest()
    }

    override suspend fun performTokenRequest(tokenRequest: TokenRequest) {
        withContext(Dispatchers.IO) {
            val tokens = authService.performTokenRequestSuspend(tokenRequest)
            TokenStorage.accessToken = tokens.accessToken
            TokenStorage.refreshToken = tokens.refreshToken
            TokenStorage.idToken = tokens.idToken
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

    override fun getEndSessionRequestIntent(customTabsIntent: CustomTabsIntent): Intent {
        return authService.getEndSessionRequestIntent(customTabsIntent)
    }

    override fun isUserLoggedIn(): Boolean {
        return with(TokenStorage) {
            accessToken != null && refreshToken != null && idToken != null
        }
    }

    override fun disposeAuthService() {
        authService.disposeAuthService()
    }

    override suspend fun checkConnection(): Int {
        val url = URL("${AuthConfig.AUTH_URI}/?${AuthConfig.CLIENT_KEY}=${AuthConfig.CLIENT_ID}")
        val connection = url.openConnection() as HttpURLConnection

        return try {
            connection.requestMethod = "GET"
            connection.connect()
            val responseCode = connection.responseCode
            connection.disconnect()
            responseCode
        } catch (t: Throwable) {
            t.printStackTrace()
            404
        } finally {
            connection.disconnect();
        }

    }
}