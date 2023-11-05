package com.skillbox.unsplash.data.collections.room

import com.skillbox.unsplash.data.collections.room.model.CollectionRoomModel

interface CollectionsRoomRepository {

    suspend fun save(collections: List<CollectionRoomModel>)

    suspend fun getAll()

    suspend fun getCollectionImages()
}