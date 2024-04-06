package com.skillbox.unsplash.domain.model.db.collection.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.skillbox.unsplash.data.local.contract.CollectionContract
import com.skillbox.unsplash.data.local.contract.CollectionImageContract
import com.skillbox.unsplash.data.local.contract.ImageContract
import com.skillbox.unsplash.domain.model.db.CollectionEntity
import com.skillbox.unsplash.domain.model.db.ImageEntity
import com.skillbox.unsplash.domain.model.db.collection.CollectionImageCrossRefEntity

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
