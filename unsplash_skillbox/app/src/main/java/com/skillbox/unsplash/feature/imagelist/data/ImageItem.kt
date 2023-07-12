package com.skillbox.unsplash.feature.imagelist.data

data class ImageItem(
    val id: String,
    val likes: Int,
    var likedByUser: Boolean,
    val authorName: String,
    val authorNickname: String,
    val authorAvatarUrl: String,
    val imageUrl: String
)
