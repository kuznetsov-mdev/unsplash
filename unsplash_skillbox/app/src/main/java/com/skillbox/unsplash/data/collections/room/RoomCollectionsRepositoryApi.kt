package com.skillbox.unsplash.data.collections.room

import androidx.paging.PagingSource
import com.skillbox.unsplash.data.collections.room.model.relations.CollectionWithUserAndImagesEntity

interface RoomCollectionsRepositoryApi {

    suspend fun insertAll(collections: List<CollectionWithUserAndImagesEntity>)

    suspend fun getCollectionImages()

    suspend fun refresh(collections: List<CollectionWithUserAndImagesEntity>)

    fun getCollections(userName: String?): PagingSource<Int, CollectionWithUserAndImagesEntity>

    suspend fun clearAll()
}