package com.skillbox.unsplash.domain.model.db.image

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.unsplash.data.local.db.contract.ImageContract
import com.skillbox.unsplash.domain.model.db.ImageEntity
import com.skillbox.unsplash.domain.model.db.UserEntity
import com.skillbox.unsplash.domain.model.db.user.UserContract

data class ImageWithUserEntity(
    @Embedded
    val image: ImageEntity,
    @Relation(
        parentColumn = ImageContract.Columns.AUTHOR_ID,
        entityColumn = UserContract.Columns.ID
    )
    val user: UserEntity
)
