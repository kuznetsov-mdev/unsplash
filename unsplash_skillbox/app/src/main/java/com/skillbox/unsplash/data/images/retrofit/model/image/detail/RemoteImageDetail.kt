package com.skillbox.unsplash.data.images.retrofit.model.image.detail

import com.skillbox.unsplash.data.images.retrofit.model.RemoteImagePreviewUrls
import com.skillbox.unsplash.data.images.retrofit.model.author.RemoteUser
import com.skillbox.unsplash.data.images.retrofit.model.image.detail.location.RemoteLocation
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class RemoteImageDetail(
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
    val urls: RemoteImagePreviewUrls,
    @Json(name = "downloads")
    val downloads: Int,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int,
    @Json(name = "exif")
    val exif: RemoteExif,
    @Json(name = "location")
    val location: RemoteLocation,
    @Json(name = "tags")
    val tags: List<RemoteTag>,
    @Json(name = "links")
    val links: RemoteLinks
)
