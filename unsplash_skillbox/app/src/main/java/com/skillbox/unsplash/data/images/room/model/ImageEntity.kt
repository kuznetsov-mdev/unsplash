package com.skillbox.unsplash.data.images.room.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.skillbox.unsplash.data.images.room.contract.AuthorContract
import com.skillbox.unsplash.data.images.room.contract.ImageContract
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = ImageContract.TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = AuthorEntity::class,
            parentColumns = [AuthorContract.Columns.ID],
            childColumns = [ImageContract.Columns.AUTHOR_ID]
        ),
    ]
)
@Parcelize
data class ImageEntity(
    @PrimaryKey
    @ColumnInfo(name = ImageContract.Columns.ID)
    val id: String,
    @ColumnInfo(name = ImageContract.Columns.AUTHOR_ID)
    val authorId: String,
    @ColumnInfo(name = ImageContract.Columns.LIKES)
    val likes: Int,
    @ColumnInfo(name = ImageContract.Columns.LIKED_BY_USER)
    val likedByUser: Boolean,
    @ColumnInfo(name = ImageContract.Columns.PREVIEW)
    val preview: String
) : Parcelable
