package com.skillbox.unsplash.feature.images.model

data class UserUiModel(
    val id: String,
    val nickname: String,
    val name: String,
    val biography: String,
    val avatarUrl: String,
    val cachedAvatarLocation: String
)
