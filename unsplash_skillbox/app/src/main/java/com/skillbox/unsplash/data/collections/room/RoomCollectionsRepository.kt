package com.skillbox.unsplash.data.collections.room

import androidx.paging.PagingSource
import com.skillbox.unsplash.data.collections.room.model.relations.CollectionWithUserAndImagesRoomModel
import com.skillbox.unsplash.data.images.room.model.UserRoomModel

interface RoomCollectionsRepository {

    suspend fun insertAll(collections: List<CollectionWithUserAndImagesRoomModel>)

    suspend fun getCollectionImages()

    suspend fun refresh(collections: List<CollectionWithUserAndImagesRoomModel>)

    suspend fun clear(users: List<UserRoomModel>)

    fun getPagingSource(): PagingSource<Int, CollectionWithUserAndImagesRoomModel>
}