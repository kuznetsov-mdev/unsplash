package com.skillbox.unsplash.domain.model

data class ImageWithUserModel(
    val image: ImageModel,
    val author: UserModel,
)
