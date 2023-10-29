package com.skillbox.unsplash.data.model.retrofit.image

import com.skillbox.unsplash.data.model.retrofit.user.RetrofitUserModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RetrofitImageModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "likes")
    val likes: Int,
    @Json(name = "liked_by_user")
    val likedByUser: Boolean,
    @Json(name = "user")
    val user: RetrofitUserModel,
    @Json(name = "urls")
    val urls: RetrofitImagePreviewModel
)
