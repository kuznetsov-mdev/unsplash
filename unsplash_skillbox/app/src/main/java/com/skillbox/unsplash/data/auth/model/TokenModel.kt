package com.skillbox.unsplash.data.auth.model

data class TokenModel(
    var accessToken: String,
    var tokenType: String,
    var scope: String,
    var createdAt: Long
)