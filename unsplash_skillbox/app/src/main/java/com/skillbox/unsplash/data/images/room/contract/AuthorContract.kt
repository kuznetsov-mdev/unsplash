package com.skillbox.unsplash.data.images.room.contract

object AuthorContract {
    const val TABLE_NAME = "authors"

    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val NICKNAME = "nickname"
        const val PROFILE_IMAGE = "profile_image"
        const val CACHED_PROFILE_IMAGE = "cached_profile_image"
    }
}