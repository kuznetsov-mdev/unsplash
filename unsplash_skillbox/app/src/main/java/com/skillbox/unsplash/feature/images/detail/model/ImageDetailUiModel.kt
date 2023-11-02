package com.skillbox.unsplash.feature.images.detail.model

import com.skillbox.unsplash.feature.images.model.ImageUiModel
import com.skillbox.unsplash.feature.images.model.UserUiModel

data class ImageDetailUiModel(
    val image: ImageUiModel,
    val width: Int,
    val height: Int,
    val author: UserUiModel,
    val exif: ExifUiModel,
    val tags: List<String>,
    val location: LocationUiModel,
    val statistic: StatisticUiModel,
    val downloadLink: String
)
