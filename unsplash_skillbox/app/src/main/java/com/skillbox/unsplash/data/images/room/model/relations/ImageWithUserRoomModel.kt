package com.skillbox.unsplash.data.images.room.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.unsplash.data.images.room.contract.AuthorContract
import com.skillbox.unsplash.data.images.room.contract.ImageContract
import com.skillbox.unsplash.data.images.room.model.ImageRoomModel
import com.skillbox.unsplash.data.images.room.model.UserRoomModel

data class ImageWithUserRoomModel(
    @Embedded
    val image: ImageRoomModel,
    @Relation(
        parentColumn = ImageContract.Columns.AUTHOR_ID,
        entityColumn = AuthorContract.Columns.ID
    )
    val author: UserRoomModel
)
