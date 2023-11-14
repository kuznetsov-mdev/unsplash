package com.skillbox.unsplash.data.collections.room

import androidx.paging.PagingSource
import com.skillbox.unsplash.data.collections.room.model.relations.CollectionWithUserAndImagesEntity
import com.skillbox.unsplash.data.user.room.model.UserEntity

interface RoomCollectionsRepositoryApi {

    suspend fun insertAll(collections: List<CollectionWithUserAndImagesEntity>)

    suspend fun getCollectionImages()

    suspend fun refresh(collections: List<CollectionWithUserAndImagesEntity>)

    suspend fun clear(users: List<UserEntity>)

    fun getPagingSource(): PagingSource<Int, CollectionWithUserAndImagesEntity>
}