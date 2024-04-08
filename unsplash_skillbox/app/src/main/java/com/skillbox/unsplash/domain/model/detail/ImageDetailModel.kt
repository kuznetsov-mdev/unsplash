package com.skillbox.unsplash.domain.model.detail

import com.skillbox.unsplash.domain.model.ImageModel
import com.skillbox.unsplash.domain.model.UserModel

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
