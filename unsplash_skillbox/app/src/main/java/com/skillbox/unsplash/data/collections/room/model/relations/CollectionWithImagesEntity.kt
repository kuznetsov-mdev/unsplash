package com.skillbox.unsplash.data.collections.room.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.skillbox.unsplash.data.collections.room.contract.CollectionContract
import com.skillbox.unsplash.data.collections.room.contract.CollectionImageContract
import com.skillbox.unsplash.data.collections.room.model.CollectionEntity
import com.skillbox.unsplash.data.collections.room.model.CollectionImageCrossRefEntity
import com.skillbox.unsplash.data.images.room.contract.ImageContract
import com.skillbox.unsplash.data.images.room.model.ImageEntity

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
