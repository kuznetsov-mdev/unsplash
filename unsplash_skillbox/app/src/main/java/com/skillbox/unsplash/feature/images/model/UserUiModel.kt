package com.skillbox.unsplash.feature.images.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserUiModel(
    val id: String,
    val nickname: String,
    val name: String,
    val biography: String,
    val avatarUrl: String,
    val cachedAvatarLocation: String
) : Parcelable
