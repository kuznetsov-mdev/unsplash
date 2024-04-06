package com.skillbox.unsplash.domain.model.local.detail

import com.skillbox.unsplash.domain.model.local.ImageUiModel
import com.skillbox.unsplash.domain.model.local.UserUiModel

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
