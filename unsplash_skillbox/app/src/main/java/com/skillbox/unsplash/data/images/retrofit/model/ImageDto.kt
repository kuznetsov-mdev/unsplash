package com.skillbox.unsplash.data.images.retrofit.model

import com.skillbox.unsplash.data.user.retrofit.UserDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ImageDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "likes")
    val likes: Int,
    @Json(name = "liked_by_user")
    val likedByUser: Boolean,
    @Json(name = "user")
    val user: UserDto,
    @Json(name = "urls")
    val urls: PreviewDto
)
