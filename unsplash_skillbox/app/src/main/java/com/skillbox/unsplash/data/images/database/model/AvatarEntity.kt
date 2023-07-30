package com.skillbox.unsplash.data.images.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skillbox.unsplash.data.images.database.contract.AvatarContract
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

@Entity(tableName = AvatarContract.TABLE_NAME)
@Parcelize
data class AvatarEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = AvatarContract.Columns.ID)
    val id: String,
    @ColumnInfo(name = AvatarContract.Columns.LARGE)
    val large: String,
    @ColumnInfo(name = AvatarContract.Columns.MEDIUM)
    val medium: String,
    @ColumnInfo(name = AvatarContract.Columns.SMALL)
    val small: String
) : Parcelable
