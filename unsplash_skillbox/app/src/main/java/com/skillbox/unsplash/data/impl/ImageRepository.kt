package com.skillbox.unsplash.data.impl

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.work.WorkInfo
import com.skillbox.unsplash.common.extensions.toImageUiModel
import com.skillbox.unsplash.data.common.SearchCondition
import com.skillbox.unsplash.data.impl.paging.ImageRemoteMediator
import com.skillbox.unsplash.domain.api.repository.RetrofitImageRepositoryApi
import com.skillbox.unsplash.domain.api.repository.RoomImageRepositoryApi
import com.skillbox.unsplash.domain.model.local.ImageWithUserModel
import com.skillbox.unsplash.domain.model.local.detail.ImageDetailModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ImageRepository(
    private val context: Application,
    private val diskImageRepository: DiskImageRepository,
    private val roomImageRepository: RoomImageRepositoryApi,
    private val retrofitImageRepository: RetrofitImageRepositoryApi
) {
    @OptIn(ExperimentalPagingApi::class)
    fun search(searchCondition: SearchCondition): Flow<PagingData<ImageWithUserModel>> {
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

    suspend fun getImageDetailInfo(imageId: String): ImageDetailModel {
        return retrofitImageRepository.getImageDetailInfo(imageId)
    }

    suspend fun setLike(imageId: String) {
        retrofitImageRepository.setLike(imageId)
    }

    suspend fun removeLike(imageId: String) {
        retrofitImageRepository.removeLike(imageId)
    }

    fun startImageSavingToGalleryWork(name: String, url: String): LiveData<WorkInfo> {
        return diskImageRepository.startImageSavingToExternalStorageWork(name, url)
    }

    private companion object {
        const val PAGE_SIZE = 10
    }

    suspend fun clearAllData() {
        diskImageRepository.removeAllFromInternalStorage()
        roomImageRepository.clearAll()
    }
}