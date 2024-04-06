package com.skillbox.unsplash.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CollectionModel(
    val id: String,
    val title: String,
    val description: String,
    val count: Int,
    val cachedCoverPhoto: String,
    val user: UserModel,
) : Parcelable