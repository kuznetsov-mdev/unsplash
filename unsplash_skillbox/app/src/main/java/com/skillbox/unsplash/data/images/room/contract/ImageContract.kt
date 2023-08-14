package com.skillbox.unsplash.data.images.room.contract

object ImageContract {
    const val TABLE_NAME = "images"

    object Columns {
        const val ID = "id"
        const val DESCRIPTION = "description"
        const val AUTHOR_ID = "author_id"
        const val LIKES = "likes"
        const val LIKED_BY_USER = "liked_by_user"
        const val PREVIEW = "preview"
        const val CACHED_PREVIEW = "cached_preview"
    }
}