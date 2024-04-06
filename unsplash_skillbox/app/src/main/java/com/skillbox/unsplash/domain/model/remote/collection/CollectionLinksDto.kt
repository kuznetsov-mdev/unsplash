package com.skillbox.unsplash.domain.model.remote.collection

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionLinksDto(
    @Json(name = "self")
    val self: String,
    @Json(name = "html")
    val html: String,
    @Json(name = "photos")
    val photos: String,
    @Json(name = "related")
    val related: String
)
