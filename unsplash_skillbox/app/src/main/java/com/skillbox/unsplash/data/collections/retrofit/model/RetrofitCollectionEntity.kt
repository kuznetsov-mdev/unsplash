package com.skillbox.unsplash.data.collections.retrofit.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RetrofitCollectionEntity(
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String,
    @Json(name = "published_at")
    val publishedAt: String,
    @Json(name = "last_collected_at")
    val lastCollectedAt: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "total_photos")
    val totalPhotos: Int,
)
