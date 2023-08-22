package com.skillbox.unsplash.data.images.retrofit.model.image.detail

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteExif(
    @Json(name = "make")
    val make: String?,
    @Json(name = "model")
    val model: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "exposure_time")
    val exposureTime: String?,
    @Json(name = "focal_length")
    val focalLength: String?,
    @Json(name = "aperture")
    val aperture: String?,
    @Json(name = "iso")
    val iso: Int?,
)
