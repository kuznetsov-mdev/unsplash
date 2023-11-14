package com.skillbox.unsplash.data.collections

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.skillbox.unsplash.common.extensions.toCollectionUiModel
import com.skillbox.unsplash.data.collections.retrofit.RetrofitCollectionsRepository
import com.skillbox.unsplash.data.collections.room.RoomCollectionsRepository
import com.skillbox.unsplash.data.collections.room.paging.CollectionRemoteMediator
import com.skillbox.unsplash.data.images.storage.DiskImageRepository
import com.skillbox.unsplash.feature.collections.model.CollectionUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CollectionsRepository @Inject constructor(
    private val context: Application,
    private val diskImageRepository: DiskImageRepository,
    private val retrofitCollectionsRepository: RetrofitCollectionsRepository,
    private val roomCollectionsRepository: RoomCollectionsRepository
) {
    @OptIn(ExperimentalPagingApi::class)
    suspend fun getAll(): Flow<PagingData<CollectionUiModel>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = CollectionRemoteMediator(
                retrofitCollectionsRepository,
                roomCollectionsRepository,
                diskImageRepository,
                context
            ),
            pagingSourceFactory = { roomCollectionsRepository.getPagingSource() }
        ).flow
            .map { pagingData ->
                pagingData.map { collectionRoomModel -> collectionRoomModel.toCollectionUiModel() }
            }
    }

    private companion object {
        const val PAGE_SIZE = 10
    }
}