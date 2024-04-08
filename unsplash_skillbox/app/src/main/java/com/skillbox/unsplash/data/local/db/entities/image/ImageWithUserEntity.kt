package com.skillbox.unsplash.data.local.db.entities.image

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.unsplash.data.local.db.contract.ImageContract
import com.skillbox.unsplash.data.local.db.entities.ImageEntity
import com.skillbox.unsplash.data.local.db.entities.UserEntity
import com.skillbox.unsplash.data.local.db.entities.user.UserContract

data class ImageWithUserEntity(
    @Embedded
    val image: ImageEntity,
    @Relation(
        parentColumn = ImageContract.Columns.AUTHOR_ID,
        entityColumn = UserContract.Columns.ID
    )
    val user: UserEntity
)
