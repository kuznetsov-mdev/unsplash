package com.skillbox.unsplash.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skillbox.unsplash.data.collections.room.dao.CollectionDao
import com.skillbox.unsplash.data.collections.room.dao.CollectionImageDao
import com.skillbox.unsplash.data.db.UnsplashRoomDataBase.Companion.DB_VERSION
import com.skillbox.unsplash.data.images.room.dao.ImageDao
import com.skillbox.unsplash.data.images.room.model.ImageEntity
import com.skillbox.unsplash.data.user.room.dao.UserDao
import com.skillbox.unsplash.domain.model.db.CollectionEntity
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

    abstract fun userDao(): UserDao

    companion object {
        const val DB_VERSION = 7
        const val DB_NAME = "unsplash_project_db"
    }
}