package com.skillbox.unsplash.data.model.retrofit.image.detail

import com.skillbox.unsplash.data.model.retrofit.image.RetrofitImagePreviewModel
import com.skillbox.unsplash.data.model.retrofit.image.detail.location.RetrofitLocationModel
import com.skillbox.unsplash.data.model.retrofit.user.RetrofitUserModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class RetrofitImageDetailModel(
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
    val urls: RetrofitImagePreviewModel,
    @Json(name = "downloads")
    val downloads: Int,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int,
    @Json(name = "exif")
    val exif: RetrofitExifModel,
    @Json(name = "location")
    val location: RetrofitLocationModel,
    @Json(name = "tags")
    val tags: List<RetrofitTagModel>,
    @Json(name = "links")
    val links: RetrofitLinksModel
)
