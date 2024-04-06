package com.skillbox.unsplash.domain.model.local.detail

import com.skillbox.unsplash.domain.model.local.ImageModel
import com.skillbox.unsplash.domain.model.local.UserModel

data class ImageDetailModel(
    val image: ImageModel,
    val width: Int,
    val height: Int,
    val author: UserModel,
    val exif: ExifModel,
    val tags: List<String>,
    val location: LocationModel,
    val statistic: StatisticModel,
    val downloadLink: String
)
