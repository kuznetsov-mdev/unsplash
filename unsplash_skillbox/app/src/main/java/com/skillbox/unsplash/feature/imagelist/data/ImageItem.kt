package com.skillbox.unsplash.feature.imagelist.data

data class ImageItem(
    val id: String,
    val authorId: String,
    val description: String,
    var likes: Int,
    var likedByUser: Boolean,
    val authorName: String,
    val authorNickname: String,
    val authorAvatarUrl: String,
    val cachedAvatarPath: String,
    val imageUrl: String,
    val cachedImagePath: String
)
