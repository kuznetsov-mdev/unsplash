package com.skillbox.unsplash.data.collections.room.model.relations

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.skillbox.unsplash.data.collections.room.contract.CollectionContract
import com.skillbox.unsplash.data.collections.room.model.CollectionImageCrossRef
import com.skillbox.unsplash.data.collections.room.model.CollectionRoomModel
import com.skillbox.unsplash.data.images.room.contract.ImageContract
import com.skillbox.unsplash.data.images.room.model.ImageRoomModel

data class CollectionWithImagesRoomModel(
    @Embedded
    val collection: CollectionRoomModel,
    @Relation(
        parentColumn = CollectionContract.Columns.ID,
        entityColumn = ImageContract.Columns.ID,
        associateBy = Junction(CollectionImageCrossRef::class)
    )
    val images: List<ImageRoomModel>
)
