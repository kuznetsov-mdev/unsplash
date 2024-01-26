package com.skillbox.unsplash.data.common

import androidx.room.withTransaction
import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.common.storage.DiskImageRepository
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val dataBase: UnsplashRoomDataBase,
    private val internalStorage: DiskImageRepository,
) : AppRepositoryApi {

    override suspend fun clearAllData() {
        dataBase.withTransaction {
            dataBase.collectionImageDao().clearAll()
            dataBase.imageDao().clearAll()
            dataBase.collectionDao().clearAll()
            dataBase.userDao().clearAll()
        }
        internalStorage.removeAllFromInternalStorage()
    }
}