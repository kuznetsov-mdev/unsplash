package com.skillbox.unsplash.domain.model.local.detail

data class ExifModel(
    val make: String,
    val model: String,
    val name: String,
    val focalLength: String,
    val aperture: String,
    val exposureTime: String,
    val iso: Int
)