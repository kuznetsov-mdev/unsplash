package com.skillbox.unsplash.data.images.retrofit.model.detail

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TagDto(
    @Json(name = "title")
    val title: String
)