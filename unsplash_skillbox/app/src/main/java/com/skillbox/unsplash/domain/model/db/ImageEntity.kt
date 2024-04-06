package com.skillbox.unsplash.domain.model.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.skillbox.unsplash.data.local.contract.ImageContract
import com.skillbox.unsplash.domain.model.db.user.UserContract
import kotlinx.parcelize.Parcelize

@Entity(
    tableName = ImageContract.TABLE_NAME,
    indices = [Index(ImageContract.Columns.DESCRIPTION)],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = [UserContract.Columns.ID],
            childColumns = [ImageContract.Columns.AUTHOR_ID]
        ),
    ]
)
@Parcelize
data class ImageEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ImageContract.Columns.ID)
    val id: String,
    @ColumnInfo(name = ImageContract.Columns.AUTHOR_ID)
    val authorId: String,
    @ColumnInfo(name = ImageContract.Columns.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = ImageContract.Columns.LIKES)
    val likes: Int,
    @ColumnInfo(name = ImageContract.Columns.LIKED_BY_USER)
    val likedByUser: Boolean,
    @ColumnInfo(name = ImageContract.Columns.PREVIEW)
    val preview: String,
    @ColumnInfo(name = ImageContract.Columns.CACHED_PREVIEW)
    val cachedPreview: String,
    @ColumnInfo(name = ImageContract.Columns.SEARCH_QUERY)
    val searchQuery: String
) : Parcelable
