package com.skillbox.unsplash.domain.model.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skillbox.unsplash.domain.model.db.user.UserContract
import kotlinx.android.parcel.Parcelize

@Entity(tableName = UserContract.TABLE_NAME)
@Parcelize
data class UserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = UserContract.Columns.ID)
    val id: String,
    @ColumnInfo(name = UserContract.Columns.NAME)
    val name: String,
    @ColumnInfo(name = UserContract.Columns.NICKNAME)
    val nickName: String,
    @ColumnInfo(name = UserContract.Columns.PROFILE_IMAGE)
    val profileImage: String,
    @ColumnInfo(name = UserContract.Columns.CACHED_PROFILE_IMAGE)
    val cachedProfileImage: String,
    @ColumnInfo(name = UserContract.Columns.BIO)
    val biography: String
) : Parcelable