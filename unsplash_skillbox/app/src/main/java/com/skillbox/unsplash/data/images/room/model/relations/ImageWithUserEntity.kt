package com.skillbox.unsplash.data.images.room.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.unsplash.data.images.room.contract.ImageContract
import com.skillbox.unsplash.data.images.room.model.ImageEntity
import com.skillbox.unsplash.data.user.room.contract.UserContract
import com.skillbox.unsplash.data.user.room.model.UserEntity

data class ImageWithUserEntity(
    @Embedded
    val image: ImageEntity,
    @Relation(
        parentColumn = ImageContract.Columns.AUTHOR_ID,
        entityColumn = UserContract.Columns.ID
    )
    val user: UserEntity
)
