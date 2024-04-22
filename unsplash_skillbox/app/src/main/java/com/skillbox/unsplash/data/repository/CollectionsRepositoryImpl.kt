package com.skillbox.unsplash.data.repository

import android.app.Application
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skillbox.unsplash.data.local.datasource.CollectionsLocalDataSourceApi
import com.skillbox.unsplash.data.local.db.entities.relations.CollectionWithUserAndImagesEntity
import com.skillbox.unsplash.data.remote.network.Network
import com.skillbox.unsplash.data.repository.paging.CollectionRemoteMediator
import com.skillbox.unsplash.domain.api.repository.CollectionRepositoryApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CollectionsRepositoryImpl @Inject constructor(
    private val context: Application,
    private val network: Network,
    private val deviceStorageRepository: DeviceStorageRepository,
    private val collectionsLocalDataSource: CollectionsLocalDataSourceApi
) : CollectionRepositoryApi {

    @OptIn(ExperimentalPagingApi::class)
    override fun getCollections(userName: String?): Flow<PagingData<CollectionWithUserAndImagesEntity>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = CollectionRemoteMediator(
                network,
                collectionsLocalDataSource,
                deviceStorageRepository,
                context,
                userName
            ),
            pagingSourceFactory = { collectionsLocalDataSource.getCollections(userName) }
        ).flow
    }

    suspend fun clearAllData() {
        deviceStorageRepository.removeAllFromInternalStorage()
    }

    private companion object {
        const val PAGE_SIZE = 10
    }
}