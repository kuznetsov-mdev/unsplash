package com.skillbox.unsplash.data.images.database.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skillbox.unsplash.data.images.database.contract.ImageContract
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull

@Entity(tableName = ImageContract.TABLE_NAME)
@Parcelize
data class ImageEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = ImageContract.Columns.ID)
    val id: String,
    @ColumnInfo(name = ImageContract.Columns.AUTHOR_ID)
    val authorId: String,
    @ColumnInfo(name = ImageContract.Columns.PREVIEW_ID)
    val previewId: String,
    @ColumnInfo(name = ImageContract.Columns.LIKES)
    val likes: Int,
    @ColumnInfo(name = ImageContract.Columns.LIKED_BY_USER)
    val likedByUser: Boolean,
) : Parcelable
