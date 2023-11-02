package com.skillbox.unsplash.feature.images.list.model

import com.skillbox.unsplash.feature.images.model.ImageUiModel
import com.skillbox.unsplash.feature.images.model.UserUiModel

data class ImageWithUserUiModel(
    val image: ImageUiModel,
    val author: UserUiModel,
)
