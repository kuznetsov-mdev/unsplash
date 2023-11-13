package com.skillbox.unsplash.data.collections.room

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.collections.room.model.relations.CollectionWithUserAndImagesRoomModel
import com.skillbox.unsplash.data.images.room.model.UserRoomModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomCollectionsRepositoryImpl(private val dataBase: UnsplashRoomDataBase) : RoomCollectionsRepository {
    override suspend fun insertAll(collections: List<CollectionWithUserAndImagesRoomModel>) {
        withContext(Dispatchers.IO) {
            dataBase.userDao().insertUsers(collections.map { it.user })
            dataBase.collectionDao().insertAll(collections.map { it.collectionWithImages.collection })
        }
    }

    override suspend fun getCollectionImages() {
        TODO("Not yet implemented")
    }

    override suspend fun refresh(collections: List<CollectionWithUserAndImagesRoomModel>) {
        withContext(Dispatchers.IO) {
            dataBase.withTransaction {
                clear(collections.map { it.user })
                dataBase.userDao().insertUsers(collections.map { it.user })
                dataBase.collectionDao().insertAll(collections.map { it.collectionWithImages.collection })
            }
        }
    }

    override suspend fun clear(users: List<UserRoomModel>) {
        dataBase.userDao().deleteUsers(users)
        dataBase.collectionDao().clearAll()

    }

    override fun getPagingSource(): PagingSource<Int, CollectionWithUserAndImagesRoomModel> {
        return dataBase.collectionDao().getPagingSource()
    }
}