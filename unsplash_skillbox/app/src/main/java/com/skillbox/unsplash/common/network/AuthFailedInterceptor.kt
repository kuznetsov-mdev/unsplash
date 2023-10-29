package com.skillbox.unsplash.common.network

import com.skillbox.unsplash.data.auth.service.AuthServiceApi
import com.skillbox.unsplash.data.model.TokenStorageDataModel
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class AuthFailedInterceptor(
    private val tokenStorage: TokenStorageDataModel,
    private val appAuthService: AuthServiceApi
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequestTimestamp = System.currentTimeMillis()
        val originalResponse = chain.proceed(chain.request())

        return originalResponse
            .takeIf { it.code != 401 }
            ?: handleUnAuthResponse(chain, originalResponse, originalRequestTimestamp)
    }

    private fun handleUnAuthResponse(
        chain: Interceptor.Chain,
        originalResponse: Response,
        requestTimestamp: Long
    ): Response {
        var latch = getLatch()

        return when {
            latch != null && latch.count > 0 -> handleTokenIsUpdating(chain, latch, requestTimestamp)
                ?: originalResponse

            tokenUpdateTime > requestTimestamp -> updateTokenAndProceedChain(chain)
            else -> handleTokenNeedRefresh(chain) ?: originalResponse
        }
    }

    private fun handleTokenIsUpdating(
        chain: Interceptor.Chain,
        latch: CountDownLatch,
        requestTimestamp: Long
    ): Response? {
        return if (latch.await(REQUEST_TIMEOUT, TimeUnit.SECONDS)
            && tokenUpdateTime > requestTimestamp
        ) {
            updateTokenAndProceedChain(chain)
        } else {
            null
        }
    }

    private fun handleTokenNeedRefresh(
        chain: Interceptor.Chain
    ): Response? {
        return if (refreshToken()) {
            updateTokenAndProceedChain(chain)
        } else {
            null
        }
    }

    private fun updateTokenAndProceedChain(
        chain: Interceptor.Chain
    ): Response {
        val newRequest = updateOriginalCallWithNewToken(chain.request())
        return chain.proceed(newRequest)
    }

    private fun refreshToken(): Boolean {
        initLatch()

        val tokenRefreshed = runBlocking {
            runCatching {
                val refreshRequest = appAuthService.getRefreshTokenRequest(tokenStorage.refreshToken.orEmpty())
                appAuthService.performTokenRequestSuspend(refreshRequest)
            }
                .getOrNull()
                ?.let { tokens ->
                    TokenStorageDataModel.accessToken = tokens.accessToken
                    TokenStorageDataModel.refreshToken = tokens.refreshToken
                    TokenStorageDataModel.idToken = tokens.idToken
                    true
                } ?: false
        }

        if (tokenRefreshed) {
            tokenUpdateTime = System.currentTimeMillis()
        } else {
            // не удалось обновить токен, произвести логаут
//            unauthorizedHandler.onUnauthorized()
            Timber.d("logout after token refresh failure")
        }
        getLatch()?.countDown()
        return tokenRefreshed
    }

    private fun updateOriginalCallWithNewToken(request: Request): Request {
        return tokenStorage.accessToken?.let { newAccessToken ->
            request
                .newBuilder()
                .header("Authorization", newAccessToken)
                .build()
        } ?: request
    }

    companion object {

        private const val REQUEST_TIMEOUT = 30L

        @Volatile
        private var tokenUpdateTime: Long = 0L

        private var countDownLatch: CountDownLatch? = null

        @Synchronized
        fun initLatch() {
            countDownLatch = CountDownLatch(1)
        }

        @Synchronized
        fun getLatch() = countDownLatch
    }
}