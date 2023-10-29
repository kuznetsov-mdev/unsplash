package com.skillbox.unsplash.data.model.retrofit.image

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RetrofitImageSearchResultModel(
    @Json(name = "total")
    val total: String,
    @Json(name = "total_pages")
    val totalPages: String,
    @Json(name = "results")
    val result: List<RetrofitImageModel>
)