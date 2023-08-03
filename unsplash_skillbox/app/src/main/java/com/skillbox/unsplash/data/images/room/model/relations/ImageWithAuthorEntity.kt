package com.skillbox.unsplash.data.images.room.model.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.skillbox.unsplash.data.images.room.contract.AuthorContract
import com.skillbox.unsplash.data.images.room.contract.ImageContract
import com.skillbox.unsplash.data.images.room.model.AuthorEntity
import com.skillbox.unsplash.data.images.room.model.ImageEntity

data class ImageWithAuthorEntity(
    @Embedded
    val image: ImageEntity,
    @Relation(
        parentColumn = ImageContract.Columns.AUTHOR_ID,
        entityColumn = AuthorContract.Columns.ID
    )
    val author: AuthorEntity
)
