package com.skillbox.unsplash.data.remote.token

data class TokensModel (
    val accessToken: String,
    val refreshToken: String,
    val idToken: String
)