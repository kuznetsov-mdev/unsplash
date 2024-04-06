package com.skillbox.unsplash.domain.model.local

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserModel(
    val id: String,
    val nickname: String,
    val name: String,
    val biography: String,
    val avatarUrl: String,
    val cachedAvatarLocation: String
) : Parcelable
