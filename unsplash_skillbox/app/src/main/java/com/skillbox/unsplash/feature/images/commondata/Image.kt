package com.skillbox.unsplash.feature.images.commondata

data class Image(
    val id: String,
    val description: String,
    var likes: Int,
    var likedByUser: Boolean,
    val url: String,
    val cachedLocation: String,
)
