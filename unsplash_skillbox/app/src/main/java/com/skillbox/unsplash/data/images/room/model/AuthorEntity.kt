package com.skillbox.unsplash.data.images.room.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skillbox.unsplash.data.images.room.contract.AuthorContract
import kotlinx.parcelize.Parcelize

@Entity(tableName = AuthorContract.TABLE_NAME)
@Parcelize
data class AuthorEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = AuthorContract.Columns.ID)
    val id: String,
    @ColumnInfo(name = AuthorContract.Columns.NAME)
    val name: String,
    @ColumnInfo(name = AuthorContract.Columns.NICKNAME)
    val nickName: String,
    @ColumnInfo(name = AuthorContract.Columns.PROFILE_IMAGE)
    val profileImage: String,
    @ColumnInfo(name = AuthorContract.Columns.CACHED_PROFILE_IMAGE)
    val cachedProfileImage: String
) : Parcelable