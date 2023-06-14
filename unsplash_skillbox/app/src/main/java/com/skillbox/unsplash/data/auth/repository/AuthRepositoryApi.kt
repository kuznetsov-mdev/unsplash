package com.skillbox.unsplash.data.auth.repository

import net.openid.appauth.AuthorizationRequest
import net.openid.appauth.AuthorizationService
import net.openid.appauth.EndSessionRequest
import net.openid.appauth.TokenRequest

interface AuthRepositoryApi {

    fun corruptAccessToken()

    fun logout()

    fun getAuthRequest(): AuthorizationRequest

    fun getEndSessionRequest(): EndSessionRequest

    suspend fun performTokenRequest(authService: AuthorizationService, tokenRequest: TokenRequest)
}