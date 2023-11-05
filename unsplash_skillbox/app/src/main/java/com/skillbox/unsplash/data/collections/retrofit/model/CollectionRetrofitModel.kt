package com.skillbox.unsplash.data.collections.retrofit.model

import com.skillbox.unsplash.data.images.retrofit.model.user.UserRetrofitModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CollectionRetrofitModel(
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
    val coverPhoto: CoverPhotoRetrofitModel,
    @Json(name = "user")
    val user: UserRetrofitModel,
    @Json(name = "links")
    val links: CollectionLinksRetrofitModel
)
