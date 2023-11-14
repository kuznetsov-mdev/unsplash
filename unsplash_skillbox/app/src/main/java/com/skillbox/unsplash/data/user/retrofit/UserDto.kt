package com.skillbox.unsplash.data.user.retrofit

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "username")
    val nickname: String,
    @Json(name = "profile_image")
    val profileImage: AvatarDto,
    @Json(name = "bio")
    val biography: String?
)
