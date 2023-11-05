package com.skillbox.unsplash.data.collections.room.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.unsplash.data.collections.room.contract.CollectionContract
import com.skillbox.unsplash.data.images.room.contract.AuthorContract
import com.skillbox.unsplash.data.images.room.model.UserRoomModel

data class CollectionWithUserAndImagesRoomModel(
    @Embedded
    val collection: CollectionWithImagesRoomModel,
    @Relation(
        parentColumn = CollectionContract.Columns.AUTHOR_ID,
        entityColumn = AuthorContract.Columns.ID
    )
    val user: UserRoomModel
)
