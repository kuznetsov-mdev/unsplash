package com.skillbox.unsplash.domain.api.repository

import androidx.paging.PagingData
import com.skillbox.unsplash.data.local.db.entities.relations.CollectionWithUserAndImagesEntity
import kotlinx.coroutines.flow.Flow

interface CollectionRepositoryApi {

    fun getCollections(userName: String?): Flow<PagingData<CollectionWithUserAndImagesEntity>>

}