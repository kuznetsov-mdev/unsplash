package com.skillbox.unsplash.domain.model.local

data class ImageWithUserUiModel(
    val image: ImageUiModel,
    val author: UserUiModel,
)
