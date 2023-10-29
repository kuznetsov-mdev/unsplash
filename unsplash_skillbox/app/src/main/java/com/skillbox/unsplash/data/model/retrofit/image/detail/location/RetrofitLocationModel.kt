package com.skillbox.unsplash.data.model.retrofit.image.detail.location

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RetrofitLocationModel(
    @Json(name = "city")
    val city: String?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "position")
    val position: RetrofitPositionModel
)
