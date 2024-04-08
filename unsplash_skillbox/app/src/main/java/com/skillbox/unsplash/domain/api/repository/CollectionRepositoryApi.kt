package com.skillbox.unsplash.domain.api.repository

import androidx.paging.PagingSource
import com.skillbox.unsplash.data.local.db.entities.relations.CollectionWithUserAndImagesEntity

interface CollectionRepositoryApi {

    suspend fun insertAll(collections: List<CollectionWithUserAndImagesEntity>)

    suspend fun getCollectionImages()

    suspend fun refresh(collections: List<CollectionWithUserAndImagesEntity>)

    fun getCollections(userName: String?): PagingSource<Int, CollectionWithUserAndImagesEntity>

    suspend fun clearAll()
}