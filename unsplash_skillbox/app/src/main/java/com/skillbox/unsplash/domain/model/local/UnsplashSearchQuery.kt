package com.skillbox.unsplash.domain.model.local

data class UnsplashSearchQuery(
    val query: String?,
    val userName: String?,
    val likedByUser: Boolean
)
