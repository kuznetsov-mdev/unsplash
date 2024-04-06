package com.skillbox.unsplash.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ProfileImageDto(
    @Json(name = "large")
    val large: String,
    @Json(name = "medium")
    val medium: String,
    @Json(name = "small")
    val small: String
)
