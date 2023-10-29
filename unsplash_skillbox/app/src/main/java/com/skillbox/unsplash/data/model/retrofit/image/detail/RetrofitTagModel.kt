package com.skillbox.unsplash.data.model.retrofit.image.detail

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RetrofitTagModel(
    @Json(name = "title")
    val title: String
)
