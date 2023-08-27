package com.skillbox.unsplash.data.images.retrofit.model.image.detail.location

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteLocation(
    @Json(name = "city")
    val city: String?,
    @Json(name = "country")
    val country: String?,
    @Json(name = "name")
    val name: String?,
    @Json(name = "position")
    val position: RemotePosition
)
