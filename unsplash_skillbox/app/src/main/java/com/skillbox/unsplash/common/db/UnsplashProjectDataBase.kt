package com.skillbox.unsplash.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skillbox.unsplash.common.db.UnsplashProjectDataBase.Companion.DB_VERSION
import com.skillbox.unsplash.data.images.room.dao.ImageDao
import com.skillbox.unsplash.data.images.room.model.AuthorEntity
import com.skillbox.unsplash.data.images.room.model.ImageEntity

@Database(
    entities = [
        ImageEntity::class,
        AuthorEntity::class
    ],
    version = DB_VERSION
)
abstract class UnsplashProjectDataBase : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    companion object {
        const val DB_VERSION = 1;
        const val DB_NAME = "unsplash_project_db"
    }
}