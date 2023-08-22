package com.skillbox.unsplash.data.images.retrofit.model.image.detail

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteTag(
    @Json(name = "title")
    val title: String
)
