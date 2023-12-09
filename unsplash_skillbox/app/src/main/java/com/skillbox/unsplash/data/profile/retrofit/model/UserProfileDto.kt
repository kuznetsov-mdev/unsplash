package com.skillbox.unsplash.data.profile.retrofit.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserProfileDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "username")
    val nickname: String,
    @Json(name = "first_name")
    val firstName: String,
    @Json(name = "last_name")
    val lastName: String,
    @Json(name = "name")
    val fullName: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "location")
    val location: String?,
    @Json(name = "total_photos")
    val totalPhotos: Int,
    @Json(name = "total_likes")
    val totalLikes: Int,
    @Json(name = "total_collections")
    val totalCollections: Int,
    @Json(name = "portfolio_url")
    val profileImage: String?,
    @Json(name = "bio")
    val biography: String?
)
