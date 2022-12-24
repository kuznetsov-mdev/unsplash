package com.skillbox.unsplash.auth.model

data class TokenModel(
    var accessToken: String,
    var tokenType: String,
    var scope: String,
    var createdAt: Long
)