package com.skillbox.unsplash.domain.model.local

import android.os.Parcelable
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