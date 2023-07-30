package com.skillbox.unsplash.data.images.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skillbox.unsplash.data.images.database.contract.AvatarContract
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Entity(tableName = AvatarContract.TABLE_NAME)
@Parcelize
data class AvatarEntity(
    @PrimaryKey
    @ColumnInfo(name = AvatarContract.Columns.ID)
    val id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = AvatarContract.Columns.LARGE)
    val large: String,
    @ColumnInfo(name = AvatarContract.Columns.MEDIUM)
    val medium: String,
    @ColumnInfo(name = AvatarContract.Columns.SMALL)
    val small: String
) : Parcelable
