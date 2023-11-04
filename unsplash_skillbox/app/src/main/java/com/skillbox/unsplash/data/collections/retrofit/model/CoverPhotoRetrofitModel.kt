package com.skillbox.unsplash.data.collections.retrofit.model

import com.skillbox.unsplash.data.images.retrofit.model.PreviewRetrofitModel
import com.skillbox.unsplash.data.images.retrofit.model.detail.ImageDetailLinksRetrofitModel
import com.skillbox.unsplash.data.images.retrofit.model.user.UserRetrofitModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoverPhotoRetrofitModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int,
    @Json(name = "color")
    val color: String,
    @Json(name = "blur_hash")
    val blurHash: String,
    @Json(name = "likes")
    val likes: Int,
    @Json(name = "liked_by_user")
    val likedByUser: Boolean,
    @Json(name = "description")
    val description: String?,
    @Json(name = "user")
    val user: UserRetrofitModel,
    @Json(name = "urls")
    val urls: PreviewRetrofitModel,
    @Json(name = "links")
    val links: ImageDetailLinksRetrofitModel
)