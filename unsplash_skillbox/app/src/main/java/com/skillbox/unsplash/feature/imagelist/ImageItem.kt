package com.skillbox.unsplash.feature.imagelist

data class ImageItem(
    val id: String,
    val likes: Int,
    val likedByUser: Boolean,
    val authorName: String,
    val authorAvatarUrl: String
)
