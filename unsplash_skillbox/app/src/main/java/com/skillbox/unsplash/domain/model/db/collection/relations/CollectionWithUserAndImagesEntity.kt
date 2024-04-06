package com.skillbox.unsplash.domain.model.db.collection.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.unsplash.data.local.db.contract.CollectionContract
import com.skillbox.unsplash.domain.model.db.UserEntity
import com.skillbox.unsplash.domain.model.db.user.UserContract

data class CollectionWithUserAndImagesEntity(
    @Embedded
    val collectionWithImages: CollectionWithImagesEntity,
    @Relation(
        parentColumn = CollectionContract.Columns.AUTHOR_ID,
        entityColumn = UserContract.Columns.ID
    )
    val user: UserEntity
)
