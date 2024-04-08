package com.skillbox.unsplash.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skillbox.unsplash.data.local.db.UnsplashRoomDataBase.Companion.DB_VERSION
import com.skillbox.unsplash.data.local.db.dao.CollectionDao
import com.skillbox.unsplash.data.local.db.dao.CollectionImageDao
import com.skillbox.unsplash.data.local.db.dao.ImageDao
import com.skillbox.unsplash.data.local.db.entities.CollectionEntity
import com.skillbox.unsplash.data.local.db.entities.ImageEntity
import com.skillbox.unsplash.data.local.db.entities.UserEntity
import com.skillbox.unsplash.data.local.db.entities.collection.CollectionImageCrossRefEntity
import com.skillbox.unsplash.data.remote.retrofit.UserApi

@Database(
    entities = [
        ImageEntity::class,
        UserEntity::class,
        CollectionEntity::class,
        CollectionImageCrossRefEntity::class
    ],
    version = DB_VERSION,
    exportSchema = true
)
abstract class UnsplashRoomDataBase : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    abstract fun collectionDao(): CollectionDao

    abstract fun collectionImageDao(): CollectionImageDao

    abstract fun userDao(): UserApi

    companion object {
        const val DB_VERSION = 7
        const val DB_NAME = "unsplash_project_db"
    }
}