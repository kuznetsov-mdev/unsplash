package com.skillbox.unsplash.data.local.datasource

import androidx.paging.PagingSource
import com.skillbox.unsplash.data.local.db.entities.relations.CollectionWithUserAndImagesEntity

interface CollectionsLocalDataSourceApi {

    fun getCollections(userName: String?): PagingSource<Int, CollectionWithUserAndImagesEntity>

    suspend fun insertAll(collections: List<CollectionWithUserAndImagesEntity>)

    suspend fun getCollectionImages()

    suspend fun refresh(collections: List<CollectionWithUserAndImagesEntity>)

    suspend fun clearAll()
}