package com.skillbox.unsplash.data.remote.dto.image

import com.skillbox.unsplash.data.remote.dto.UserDto
import com.skillbox.unsplash.data.remote.dto.image.location.LocationDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ImageDetailDto(
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
    val urls: PreviewDto,
    @Json(name = "downloads")
    val downloads: Int,
    @Json(name = "width")
    val width: Int,
    @Json(name = "height")
    val height: Int,
    @Json(name = "exif")
    val exif: ExifDto,
    @Json(name = "location")
    val location: LocationDto,
    @Json(name = "tags")
    val tags: List<TagDto>,
    @Json(name = "links")
    val links: ImageDetailLinksDto
)
