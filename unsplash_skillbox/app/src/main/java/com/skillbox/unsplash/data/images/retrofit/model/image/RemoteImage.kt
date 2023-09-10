package com.skillbox.unsplash.data.images.retrofit.model.image

import com.skillbox.unsplash.data.images.retrofit.model.RemoteImagePreviewUrls
import com.skillbox.unsplash.data.images.retrofit.model.author.RemoteUser
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteImage(
    @Json(name = "id")
    val id: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "likes")
    val likes: Int,
    @Json(name = "liked_by_user")
    val likedByUser: Boolean,
    @Json(name = "user")
    val user: RemoteUser,
    @Json(name = "urls")
    val urls: RemoteImagePreviewUrls
)