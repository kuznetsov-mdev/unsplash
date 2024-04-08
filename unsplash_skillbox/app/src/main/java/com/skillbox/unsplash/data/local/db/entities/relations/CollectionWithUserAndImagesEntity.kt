package com.skillbox.unsplash.data.local.db.entities.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.unsplash.data.local.db.contract.CollectionContract
import com.skillbox.unsplash.data.local.db.entities.UserEntity
import com.skillbox.unsplash.data.local.db.entities.user.UserContract

data class CollectionWithUserAndImagesEntity(
    @Embedded
    val collectionWithImages: CollectionWithImagesEntity,
    @Relation(
        parentColumn = CollectionContract.Columns.AUTHOR_ID,
        entityColumn = UserContract.Columns.ID
    )
    val user: UserEntity
)
