package com.skillbox.unsplash.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.work.WorkInfo
import com.skillbox.unsplash.common.SearchCondition
import com.skillbox.unsplash.data.local.datasource.ImageLocalDataSourceApi
import com.skillbox.unsplash.data.local.db.entities.image.ImageWithUserEntity
import com.skillbox.unsplash.data.remote.datasource.ImageRemoteDataSourceApi
import com.skillbox.unsplash.data.repository.paging.ImageRemoteMediator
import com.skillbox.unsplash.domain.api.repository.ImageRepositoryApi
import com.skillbox.unsplash.domain.model.detail.ImageDetailModel
import kotlinx.coroutines.flow.Flow


class ImageRepositoryImpl(
    private val context: Application,
    private val deviceStorageRepository: DeviceStorageRepository,
    private val imageLocalDataSource: ImageLocalDataSourceApi,
    private val imageRemoteDataSource: ImageRemoteDataSourceApi
) : ImageRepositoryApi {
    @OptIn(ExperimentalPagingApi::class)

    override fun search(searchCondition: SearchCondition): Flow<PagingData<ImageWithUserEntity>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = ImageRemoteMediator(
                searchCondition,
                imageLocalDataSource,
                imageRemoteDataSource,
                deviceStorageRepository,
                context
            ),
            pagingSourceFactory = { imageLocalDataSource.getPagingSource(searchCondition) }
        ).flow
    }

    override suspend fun getImageDetailInfo(imageId: String): ImageDetailModel {
        return imageRemoteDataSource.getImageDetailInfo(imageId)
    }

    override suspend fun likeImage(imageId: String) {
        imageRemoteDataSource.setLike(imageId)
    }

    override suspend fun unlikeImage(imageId: String) {
        imageRemoteDataSource.removeLike(imageId)
    }

    override fun startImageSavingToGalleryWork(name: String, url: String): LiveData<WorkInfo> {
        return deviceStorageRepository.startImageSavingToExternalStorageWork(name, url)
    }

    override suspend fun clearAllData() {
        deviceStorageRepository.removeAllFromInternalStorage()
        imageLocalDataSource.clearAll()
    }

    private companion object {
        const val PAGE_SIZE = 10
    }
}