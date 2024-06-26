package com.skillbox.unsplash.data.local.datasource

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.skillbox.unsplash.data.local.CollectionsLocalDataSourceApi
import com.skillbox.unsplash.data.local.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.local.db.entities.relations.CollectionWithUserAndImagesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CollectionsLocalDataSourceImpl(private val dataBase: UnsplashRoomDataBase) : CollectionsLocalDataSourceApi {

    override suspend fun insertAll(collections: List<CollectionWithUserAndImagesEntity>) {
        withContext(Dispatchers.IO) {
            dataBase.withTransaction {
                dataBase.userDao().insertUsers(collections.map { it.user })
                dataBase.collectionDao().insertAll(collections.map { it.collectionWithImages.collection })
            }
        }
    }

    override suspend fun getCollectionImages() {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(collections: List<CollectionWithUserAndImagesEntity>) {
        withContext(Dispatchers.IO) {
            dataBase.withTransaction {
                dataBase.collectionDao().clearAll()
                dataBase.userDao().insertUsers(collections.map { it.user })
                dataBase.collectionDao().insertAll(collections.map { it.collectionWithImages.collection })
            }
        }
    }

    override fun getCollections(userName: String?): PagingSource<Int, CollectionWithUserAndImagesEntity> {
        return if (userName == null) {
            dataBase.collectionDao().getCollections()
        } else {
            dataBase.collectionDao().getUserCollections(userName)
        }
    }

    override suspend fun clearAll() {
        dataBase.collectionDao().clearAll()
    }
}