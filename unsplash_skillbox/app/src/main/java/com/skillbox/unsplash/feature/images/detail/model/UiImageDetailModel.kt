package com.skillbox.unsplash.feature.images.detail.model

import com.skillbox.unsplash.feature.images.model.UiImageModel
import com.skillbox.unsplash.feature.images.model.UiUserModel

data class UiImageDetailModel(
    val image: UiImageModel,
    val width: Int,
    val height: Int,
    val author: UiUserModel,
    val exif: UiExifModel,
    val tags: List<String>,
    val location: UiLocationModel,
    val statistic: UiStatisticModel,
    val downloadLink: String
)
