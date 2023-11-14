package com.skillbox.unsplash.data.collections.room

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.collections.room.model.relations.CollectionWithUserAndImagesEntity
import com.skillbox.unsplash.data.user.room.model.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomCollectionsRepositoryImpl(private val dataBase: UnsplashRoomDataBase) : RoomCollectionsRepositoryApi {
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
                clear(collections.map { it.user })
                dataBase.userDao().insertUsers(collections.map { it.user })
                dataBase.collectionDao().insertAll(collections.map { it.collectionWithImages.collection })
            }
        }
    }

    override suspend fun clear(users: List<UserEntity>) {
        withContext(Dispatchers.IO) {
            dataBase.withTransaction {
                dataBase.userDao().deleteUsers(users)
                dataBase.collectionDao().clearAll()
            }
        }
    }

    override fun getPagingSource(): PagingSource<Int, CollectionWithUserAndImagesEntity> {
        return dataBase.collectionDao().getPagingSource()
    }
}