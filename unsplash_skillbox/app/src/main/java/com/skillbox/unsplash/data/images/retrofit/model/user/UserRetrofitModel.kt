package com.skillbox.unsplash.data.images.retrofit.model.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UserRetrofitModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "username")
    val nickname: String,
    @Json(name = "profile_image")
    val profileImage: AvatarRetrofitModel,
    @Json(name = "bio")
    val biography: String?
)
