package com.skillbox.unsplash.data.images.database.contract

object AuthorContract {
    const val TABLE_NAME = "authors"

    object Columns {
        const val ID = "id"
        const val AVATAR_ID = "avatar_id"
        const val NAME = "name"
        const val NICKNAME = "nickname"
    }
}