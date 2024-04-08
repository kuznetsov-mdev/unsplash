package com.skillbox.unsplash.data.remote.dto

import com.skillbox.unsplash.data.remote.dto.collection.CollectionLinksDto
import com.skillbox.unsplash.data.remote.dto.collection.CoverPhotoDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "title")
    val title: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "published_at")
    val publishedAt: String,
    @Json(name = "updated_at")
    val updatedAt: String,
    @Json(name = "total_photos")
    val totalPhotos: Int,
    @Json(name = "cover_photo")
    val coverPhoto: CoverPhotoDto,
    @Json(name = "user")
    val user: UserDto,
    @Json(name = "links")
    val links: CollectionLinksDto
)
