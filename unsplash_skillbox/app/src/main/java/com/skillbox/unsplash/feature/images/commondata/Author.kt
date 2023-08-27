package com.skillbox.unsplash.feature.images.commondata

data class Author(
    val id: String,
    val nickname: String,
    val name: String,
    val biography: String,
    val avatarUrl: String,
    val cachedAvatarLocation: String
)
