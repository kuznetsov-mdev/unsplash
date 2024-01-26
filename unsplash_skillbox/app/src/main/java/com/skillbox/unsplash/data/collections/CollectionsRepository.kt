package com.skillbox.unsplash.data.collections

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.skillbox.unsplash.common.extensions.toCollectionUiModel
import com.skillbox.unsplash.data.collections.paging.CollectionRemoteMediator
import com.skillbox.unsplash.data.collections.retrofit.RetrofitCollectionsRepositoryApi
import com.skillbox.unsplash.data.collections.room.RoomCollectionsRepositoryApi
import com.skillbox.unsplash.data.common.storage.DiskImageRepository
import com.skillbox.unsplash.feature.collections.model.CollectionUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CollectionsRepository @Inject constructor(
    private val context: Application,
    private val diskImageRepository: DiskImageRepository,
    private val retrofitCollectionsRepositoryApi: RetrofitCollectionsRepositoryApi,
    private val roomCollectionsRepository: RoomCollectionsRepositoryApi
) {
    @OptIn(ExperimentalPagingApi::class)
    suspend fun getCollections(userName: String? = null): Flow<PagingData<CollectionUiModel>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = CollectionRemoteMediator(
                retrofitCollectionsRepositoryApi,
                roomCollectionsRepository,
                diskImageRepository,
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
        diskImageRepository.removeAllFromInternalStorage()
    }

    private companion object {
        const val PAGE_SIZE = 10
    }
}