package com.skillbox.unsplash.data.repository

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.skillbox.unsplash.common.extensions.toCollectionUiModel
import com.skillbox.unsplash.data.remote.network.Network
import com.skillbox.unsplash.data.repository.paging.CollectionRemoteMediator
import com.skillbox.unsplash.domain.api.repository.CollectionRepositoryApi
import com.skillbox.unsplash.domain.model.CollectionModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CollectionsRepositoryImpl @Inject constructor(
    private val context: Application,
    private val network: Network,
    private val deviceStorageRepository: DeviceStorageRepository,
    private val roomCollectionsRepository: CollectionRepositoryApi
) {
    @OptIn(ExperimentalPagingApi::class)
    suspend fun getCollections(userName: String? = null): Flow<PagingData<CollectionModel>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = CollectionRemoteMediator(
                network,
                roomCollectionsRepository,
                deviceStorageRepository,
                context,
                userName
            ),
            pagingSourceFactory = { roomCollectionsRepository.getCollections(userName) }
        ).flow
            .map { pagingData ->
                pagingData.map { collectionRoomModel -> collectionRoomModel.toCollectionUiModel() }
            }
    }

    suspend fun clearAllData() {
        deviceStorageRepository.removeAllFromInternalStorage()
    }

    private companion object {
        const val PAGE_SIZE = 10
    }
}