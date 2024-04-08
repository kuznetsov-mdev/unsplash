package com.skillbox.unsplash.data.repository

import androidx.room.withTransaction
import com.skillbox.unsplash.data.local.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.remote.retrofit.AppRepositoryApi
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val dataBase: UnsplashRoomDataBase,
    private val internalStorage: DeviceStorageRepository,
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