package com.skillbox.unsplash.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.work.WorkInfo
import com.skillbox.unsplash.common.SearchCondition
import com.skillbox.unsplash.common.extensions.toImageUiModel
import com.skillbox.unsplash.data.repository.paging.ImageRemoteMediator
import com.skillbox.unsplash.domain.api.repository.ImageRepositoryApi
import com.skillbox.unsplash.domain.api.repository.RetrofitImageRepositoryApi
import com.skillbox.unsplash.domain.api.repository.RoomImageRepositoryApi
import com.skillbox.unsplash.domain.model.ImageWithUserModel
import com.skillbox.unsplash.domain.model.detail.ImageDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ImageRepositoryImpl(
    private val context: Application,
    private val diskImageRepository: DiskImageRepository,
    private val roomImageRepository: RoomImageRepositoryApi,
    private val retrofitImageRepository: RetrofitImageRepositoryApi
) : ImageRepositoryApi {
    @OptIn(ExperimentalPagingApi::class)
    override fun search(searchCondition: SearchCondition): Flow<PagingData<ImageWithUserModel>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = ImageRemoteMediator(
                searchCondition,
                roomImageRepository,
                retrofitImageRepository,
                diskImageRepository,
                context
            ),
            pagingSourceFactory = { roomImageRepository.getPagingSource(searchCondition) }
        ).flow
            .map { pagingData ->
                pagingData.map { imageEntity -> imageEntity.toImageUiModel() }
            }
    }

    override suspend fun getImageDetailInfo(imageId: String): ImageDetailModel {
        return retrofitImageRepository.getImageDetailInfo(imageId)
    }

    override suspend fun setLike(imageId: String) {
        retrofitImageRepository.setLike(imageId)
    }

    override suspend fun removeLike(imageId: String) {
        retrofitImageRepository.removeLike(imageId)
    }

    override fun startImageSavingToGalleryWork(name: String, url: String): LiveData<WorkInfo> {
        return diskImageRepository.startImageSavingToExternalStorageWork(name, url)
    }

    override suspend fun clearAllData() {
        diskImageRepository.removeAllFromInternalStorage()
        roomImageRepository.clearAll()
    }

    private companion object {
        const val PAGE_SIZE = 10
    }
}