package com.skillbox.unsplash.feature.images.detail.data

data class Exif(
    val make: String,
    val model: String,
    val name: String,
    val focalLength: String,
    val aperture: String,
    val exposureTime: String,
    val iso: Int
)