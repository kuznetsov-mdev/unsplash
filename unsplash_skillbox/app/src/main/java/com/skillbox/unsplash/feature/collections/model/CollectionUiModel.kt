package com.skillbox.unsplash.feature.collections.model

import com.skillbox.unsplash.feature.images.model.UserUiModel

data class CollectionUiModel(
    val id: String,
    val title: String,
    val description: String,
    val user: UserUiModel,
)