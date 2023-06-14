package com.skillbox.unsplash.data.auth.model

data class TokensModel (
    val accessToken: String,
    val refreshToken: String,
    val idToken: String
)