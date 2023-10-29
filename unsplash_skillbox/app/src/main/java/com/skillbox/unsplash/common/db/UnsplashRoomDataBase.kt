package com.skillbox.unsplash.common.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.skillbox.unsplash.common.db.UnsplashRoomDataBase.Companion.DB_VERSION
import com.skillbox.unsplash.data.images.room.dao.ImageDao
import com.skillbox.unsplash.data.model.room.RoomImageModel
import com.skillbox.unsplash.data.model.room.RoomUserModel

@Database(
    entities = [
        RoomImageModel::class,
        RoomUserModel::class
    ],
    version = DB_VERSION,
    exportSchema = true
)
abstract class UnsplashRoomDataBase : RoomDatabase() {

    abstract fun imageDao(): ImageDao

    companion object {
        const val DB_VERSION = 5
        const val DB_NAME = "unsplash_project_db"
    }
}