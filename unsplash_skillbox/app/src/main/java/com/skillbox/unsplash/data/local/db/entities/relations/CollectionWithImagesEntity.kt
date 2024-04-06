package com.skillbox.unsplash.data.local.db.entities.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.skillbox.unsplash.data.local.db.contract.CollectionContract
import com.skillbox.unsplash.data.local.db.contract.CollectionImageContract
import com.skillbox.unsplash.data.local.db.contract.ImageContract
import com.skillbox.unsplash.data.local.db.entities.CollectionEntity
import com.skillbox.unsplash.data.local.db.entities.ImageEntity
import com.skillbox.unsplash.data.local.db.entities.collection.CollectionImageCrossRefEntity

data class CollectionWithImagesEntity(
    @Embedded
    val collection: CollectionEntity,
    @Relation(
        parentColumn = CollectionContract.Columns.ID,
        entity = ImageEntity::class,
        entityColumn = ImageContract.Columns.ID,
        associateBy = Junction(
            CollectionImageCrossRefEntity::class,
            parentColumn = CollectionImageContract.Columns.COLLECTION_ID,
            entityColumn = CollectionImageContract.Columns.IMAGE_ID
        )
    )
    val images: List<ImageEntity>? = null
)
