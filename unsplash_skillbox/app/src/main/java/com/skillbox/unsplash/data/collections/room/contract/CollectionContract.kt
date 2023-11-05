package com.skillbox.unsplash.data.collections.room.contract

object CollectionContract {
    const val TABLE_NAME = "collections"

    object Columns {
        const val ID = "id"
        const val AUTHOR_ID = "author_id"
        const val TITLE = "title"
        const val DESCRIPTION = "description"
        const val PUBLISHED_AT = "published_at"
        const val UPDATED_AT = "updated_at"
        const val TOTAL_PHOTOS = "total_photos"
        const val CACHED_COVER_PHOTO = "cached_cover_photo"
    }
}