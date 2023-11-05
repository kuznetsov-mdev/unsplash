package com.skillbox.unsplash.data.collections.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.skillbox.unsplash.data.collections.room.contract.CollectionImageContract

@Entity(
    tableName = CollectionImageContract.TABLE_NAME,
    primaryKeys = [
        CollectionImageContract.Columns.IMAGE_ID,
        CollectionImageContract.Columns.COLLECTION_ID
    ]
)
data class CollectionImageCrossRef(
    @ColumnInfo(name = "image_id")
    val imageId: String,
    @ColumnInfo(name = "collection_id")
    val collectionId: String
)
