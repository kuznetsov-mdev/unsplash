package com.skillbox.unsplash.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skillbox.unsplash.data.local.UnsplashRoomDataBase.Companion.DB_VERSION
import com.skillbox.unsplash.data.local.dao.CollectionDao
import com.skillbox.unsplash.data.local.dao.CollectionImageDao
import com.skillbox.unsplash.data.local.dao.ImageDao
import com.skillbox.unsplash.data.remote.retrofit.UserApi
import com.skillbox.unsplash.domain.model.db.CollectionEntity
import com.skillbox.unsplash.domain.model.db.ImageEntity
import com.skillbox.unsplash.domain.model.db.UserEntity
import com.skillbox.unsplash.domain.model.db.collection.CollectionImageCrossRefEntity

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