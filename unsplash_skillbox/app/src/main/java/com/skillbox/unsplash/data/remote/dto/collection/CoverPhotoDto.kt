package com.skillbox.unsplash.data.remote.dto.collection

import com.skillbox.unsplash.data.remote.dto.UserDto
import com.skillbox.unsplash.data.remote.dto.image.ImageDetailLinksDto
import com.skillbox.unsplash.data.remote.dto.image.PreviewDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoverPhotoDto(
    @Json(name = "id")
    val id: String,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int,
    @Json(name = "color")
    val color: String,
    @Json(name = "blur_hash")
    val blurHash: String?,
    @Json(name = "likes")
    val likes: Int,
    @Json(name = "liked_by_user")
    val likedByUser: Boolean,
    @Json(name = "description")
    val description: String?,
    @Json(name = "user")
    val user: UserDto,
    @Json(name = "urls")
    val urls: PreviewDto,
    @Json(name = "links")
    val links: ImageDetailLinksDto
)