package com.skillbox.unsplash.domain.model.remote.token

data class TokensModel (
    val accessToken: String,
    val refreshToken: String,
    val idToken: String
)