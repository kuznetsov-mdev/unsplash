package com.skillbox.unsplash.domain.usecase.collection

import androidx.paging.PagingData
import androidx.paging.map
import com.skillbox.unsplash.common.extensions.toCollectionUiModel
import com.skillbox.unsplash.domain.api.repository.CollectionRepositoryApi
import com.skillbox.unsplash.domain.model.CollectionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetCollectionsUseCase(private val repository: CollectionRepositoryApi) {

    operator fun invoke(userName: String?): Flow<PagingData<CollectionModel>> {
        return repository.getCollections(userName)
            .map { pagingData ->
                pagingData.map { collectionRoomModel ->
                    collectionRoomModel.toCollectionUiModel()
                }
            }
    }
}