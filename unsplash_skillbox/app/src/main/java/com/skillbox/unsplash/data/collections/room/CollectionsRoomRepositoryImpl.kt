package com.skillbox.unsplash.data.collections.room

import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.collections.room.model.CollectionRoomModel

class CollectionsRoomRepositoryImpl(private val dataBase: UnsplashRoomDataBase) : CollectionsRoomRepository {
    override suspend fun save(collections: List<CollectionRoomModel>) {
        dataBase.collectionDao().save(collections)
    }

    override suspend fun getAll() {
        TODO("Not yet implemented")
    }

    override suspend fun getCollectionImages() {
        TODO("Not yet implemented")
    }
}