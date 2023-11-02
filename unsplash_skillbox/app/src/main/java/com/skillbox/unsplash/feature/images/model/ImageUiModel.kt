package com.skillbox.unsplash.feature.images.model

data class ImageUiModel(
    val id: String,
    val description: String,
    var likes: Int,
    var likedByUser: Boolean,
    val url: String,
    val cachedLocation: String,
)
