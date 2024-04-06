package com.skillbox.unsplash.domain.model.local

data class ImageUiModel(
    val id: String,
    val description: String,
    var likes: Int,
    var likedByUser: Boolean,
    val url: String,
    val cachedLocation: String,
)
