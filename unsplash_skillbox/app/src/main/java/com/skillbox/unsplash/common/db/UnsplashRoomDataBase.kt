package com.skillbox.unsplash.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skillbox.unsplash.common.db.UnsplashRoomDataBase.Companion.DB_VERSION
import com.skillbox.unsplash.data.collections.room.dao.CollectionDao
import com.skillbox.unsplash.data.collections.room.model.CollectionEntity
import com.skillbox.unsplash.data.collections.room.model.CollectionImageCrossRefEntity
import com.skillbox.unsplash.data.images.room.dao.ImageDao
import com.skillbox.unsplash.data.images.room.model.ImageRoomModel
import com.skillbox.unsplash.data.images.room.model.UserRoomModel
import com.skillbox.unsplash.data.user.UserDao

@Database(
    entities = [
        ImageRoomModel::class,
        UserRoomModel::class,
        CollectionEntity::class,
        CollectionImageCrossRefEntity::class
    ],
    version = DB_VERSION,
    exportSchema = true
)
abstract class UnsplashRoomDataBase : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    abstract fun collectionDao(): CollectionDao

    abstract fun userDao(): UserDao

    companion object {
        const val DB_VERSION = 5
        const val DB_NAME = "unsplash_project_db"
    }
}