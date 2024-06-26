package com.skillbox.unsplash.data.local.db.entities.user

object UserContract {
    const val TABLE_NAME = "authors"

    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val NICKNAME = "nickname"
        const val PROFILE_IMAGE = "profile_image"
        const val CACHED_PROFILE_IMAGE = "cached_profile_image"
        const val BIO = "biography"
    }
}