package com.skillbox.unsplash.feature.collections.model

import android.os.Parcelable
import com.skillbox.unsplash.feature.images.model.UserUiModel
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionUiModel(
    val id: String,
    val title: String,
    val description: String,
    val count: Int,
    val cachedCoverPhoto: String,
    val user: UserUiModel,
) : Parcelable