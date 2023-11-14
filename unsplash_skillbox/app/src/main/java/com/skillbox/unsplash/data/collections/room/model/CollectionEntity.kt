package com.skillbox.unsplash.data.collections.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.skillbox.unsplash.data.collections.room.contract.CollectionContract

@Entity(tableName = CollectionContract.TABLE_NAME)
data class CollectionEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = CollectionContract.Columns.ID)
    val id: String,
    @ColumnInfo(name = CollectionContract.Columns.AUTHOR_ID)
    val authorId: String,
    @ColumnInfo(name = CollectionContract.Columns.TITLE)
    val title: String,
    @ColumnInfo(name = CollectionContract.Columns.DESCRIPTION)
    val description: String,
    @ColumnInfo(name = CollectionContract.Columns.PUBLISHED_AT)
    val publishedAt: String,
    @ColumnInfo(name = CollectionContract.Columns.UPDATED_AT)
    val updatedAt: String,
    @ColumnInfo(name = CollectionContract.Columns.TOTAL_PHOTOS)
    val totalPhotos: Int,
    @ColumnInfo(name = CollectionContract.Columns.CACHED_COVER_PHOTO)
    val cachedCoverPhoto: String
)
