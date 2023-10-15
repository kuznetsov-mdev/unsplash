package com.skillbox.unsplash.data.images.retrofit.model.image

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteImageSearchResult(
    @Json(name = "total")
    val total: String,
    @Json(name = "total_pages")
    val totalPages: String,
    @Json(name = "results")
    val result: List<RemoteImage>
)