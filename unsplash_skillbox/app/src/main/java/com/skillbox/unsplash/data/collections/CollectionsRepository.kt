package com.skillbox.unsplash.data.collections

import com.skillbox.unsplash.common.extensions.toUiEntity
import com.skillbox.unsplash.data.collections.retrofit.RetrofitCollectionsRepository
import com.skillbox.unsplash.feature.collections.model.CollectionUiModel
import javax.inject.Inject

class CollectionsRepository @Inject constructor(
    private val retrofitCollectionsRepository: RetrofitCollectionsRepository
) {
    suspend fun getAll(pageNumber: Int, pageSize: Int): List<CollectionUiModel> {
        return retrofitCollectionsRepository.getAll(pageNumber, pageSize).map { it.toUiEntity() }
    }
}