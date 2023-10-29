package com.skillbox.unsplash.data.model.room.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.unsplash.data.images.room.contract.AuthorContract
import com.skillbox.unsplash.data.images.room.contract.ImageContract
import com.skillbox.unsplash.data.model.room.RoomImageModel
import com.skillbox.unsplash.data.model.room.RoomUserModel

data class RoomImageWithUserModel(
    @Embedded
    val image: RoomImageModel,
    @Relation(
        parentColumn = ImageContract.Columns.AUTHOR_ID,
        entityColumn = AuthorContract.Columns.ID
    )
    val author: RoomUserModel
)
