package com.skillbox.unsplash.feature.images.model

data class UiImageModel(
    val id: String,
    val description: String,
    var likes: Int,
    var likedByUser: Boolean,
    val url: String,
    val cachedLocation: String,
)
