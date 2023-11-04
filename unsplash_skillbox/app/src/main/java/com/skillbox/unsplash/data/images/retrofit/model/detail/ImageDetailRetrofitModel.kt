package com.skillbox.unsplash.data.images.retrofit.model.detail

import com.skillbox.unsplash.data.images.retrofit.model.PreviewRetrofitModel
import com.skillbox.unsplash.data.images.retrofit.model.detail.location.LocationRetrofitModel
import com.skillbox.unsplash.data.images.retrofit.model.user.UserRetrofitModel
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ImageDetailRetrofitModel(
    @Json(name = "id")
    val id: String,
    @Json(name = "description")
    val description: String?,
    @Json(name = "likes")
    val likes: Int,
    @Json(name = "liked_by_user")
    val likedByUser: Boolean,
    @Json(name = "user")
    val user: UserRetrofitModel,
    @Json(name = "urls")
    val urls: PreviewRetrofitModel,
    @Json(name = "downloads")
    val downloads: Int,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int,
    @Json(name = "exif")
    val exif: ExifRetrofitModel,
    @Json(name = "location")
    val location: LocationRetrofitModel,
    @Json(name = "tags")
    val tags: List<TagRetrofitModel>,
    @Json(name = "links")
    val links: ImageDetailLinksRetrofitModel
)
