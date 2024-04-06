package com.skillbox.unsplash.domain.model.local

data class ImageWithUserModel(
    val image: ImageModel,
    val author: UserModel,
)
