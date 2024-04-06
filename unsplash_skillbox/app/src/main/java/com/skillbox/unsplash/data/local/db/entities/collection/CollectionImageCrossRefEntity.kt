package com.skillbox.unsplash.data.local.db.entities.collection

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.skillbox.unsplash.data.local.db.contract.CollectionImageContract

@Entity(
    tableName = CollectionImageContract.TABLE_NAME,
    primaryKeys = [
        CollectionImageContract.Columns.COLLECTION_ID,
        CollectionImageContract.Columns.IMAGE_ID
    ]
)
data class CollectionImageCrossRefEntity(
    @ColumnInfo(name = CollectionImageContract.Columns.COLLECTION_ID)
    val collectionId: String,
    @ColumnInfo(name = CollectionImageContract.Columns.IMAGE_ID)
    val imageId: String
)
