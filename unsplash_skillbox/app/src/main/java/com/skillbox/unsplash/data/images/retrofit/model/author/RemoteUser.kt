package com.skillbox.unsplash.data.images.retrofit.model.author

import com.skillbox.unsplash.data.images.retrofit.model.RemoteProfileImage
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteUser(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "username")
    val nickname: String,
    @Json(name = "profile_image")
    val profileImage: RemoteProfileImage,
    @Json(name = "bio")
    val biography: String?
)
