package com.skillbox.unsplash.auth.data

data class TokensModel (
    val accessToken: String,
    val refreshToken: String,
    val idToken: String
)