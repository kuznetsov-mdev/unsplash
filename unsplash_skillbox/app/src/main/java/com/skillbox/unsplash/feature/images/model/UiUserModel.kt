package com.skillbox.unsplash.feature.images.model

data class UiUserModel(
    val id: String,
    val nickname: String,
    val name: String,
    val biography: String,
    val avatarUrl: String,
    val cachedAvatarLocation: String
)
