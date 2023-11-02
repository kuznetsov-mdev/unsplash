package com.skillbox.unsplash.feature.images.detail.model

data class ExifUiModel(
    val make: String,
    val model: String,
    val name: String,
    val focalLength: String,
    val aperture: String,
    val exposureTime: String,
    val iso: Int
)