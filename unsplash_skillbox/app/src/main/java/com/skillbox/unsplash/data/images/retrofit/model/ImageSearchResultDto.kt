package com.skillbox.unsplash.data.images.retrofit.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageSearchResultDto(
    @Json(name = "total")
    val total: String,
    @Json(name = "total_pages")
    val totalPages: String,
    @Json(name = "results")
    val result: List<ImageDto>
)