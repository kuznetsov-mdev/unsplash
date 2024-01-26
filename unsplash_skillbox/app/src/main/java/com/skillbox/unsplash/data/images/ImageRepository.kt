package com.skillbox.unsplash.data.images

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
import com.skillbox.unsplash.data.common.storage.DiskImageRepository
import com.skillbox.unsplash.data.images.paging.ImageRemoteMediator
import com.skillbox.unsplash.data.images.retrofit.RetrofitImageRepositoryApi
import com.skillbox.unsplash.data.images.room.RoomImageRepositoryApi
import com.skillbox.unsplash.feature.images.detail.model.ImageDetailUiModel
import com.skillbox.unsplash.feature.images.list.model.ImageWithUserUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ImageRepository(
    private val context: Application,
    private val diskImageRepository: DiskImageRepository,
    private val roomImageRepository: RoomImageRepositoryApi,
    private val retrofitImageRepository: RetrofitImageRepositoryApi
) {
    @OptIn(ExperimentalPagingApi::class)
    fun search(searchCondition: SearchCondition): Flow<PagingData<ImageWithUserUiModel>> {
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

    suspend fun getImageDetailInfo(imageId: String): ImageDetailUiModel {
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