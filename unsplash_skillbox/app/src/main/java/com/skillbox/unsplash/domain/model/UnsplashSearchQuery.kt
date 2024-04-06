package com.skillbox.unsplash.domain.model

data class UnsplashSearchQuery(
    val query: String?,
    val userName: String?,
    val likedByUser: Boolean
)
