package com.skillbox.unsplash.feature.images.list.model

import com.skillbox.unsplash.feature.images.model.UiImageModel
import com.skillbox.unsplash.feature.images.model.UiUserModel

data class UiImageWithUserModel(
    val image: UiImageModel,
    val author: UiUserModel,
)
