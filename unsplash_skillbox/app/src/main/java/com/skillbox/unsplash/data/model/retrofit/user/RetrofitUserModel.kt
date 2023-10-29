package com.skillbox.unsplash.data.model.retrofit.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RetrofitUserModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "name")
    val name: String,
    @Json(name = "username")
    val nickname: String,
    @Json(name = "profile_image")
    val profileImage: RetrofitImageLinkModel,
    @Json(name = "bio")
    val biography: String?
)
