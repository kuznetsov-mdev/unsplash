package com.skillbox.unsplash.domain.model.db.collection.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.unsplash.data.collections.room.contract.CollectionContract
import com.skillbox.unsplash.data.user.room.contract.UserContract
import com.skillbox.unsplash.domain.model.db.UserEntity

data class CollectionWithUserAndImagesEntity(
    @Embedded
    val collectionWithImages: CollectionWithImagesEntity,
    @Relation(
        parentColumn = CollectionContract.Columns.AUTHOR_ID,
        entityColumn = UserContract.Columns.ID
    )
    val user: UserEntity
)
