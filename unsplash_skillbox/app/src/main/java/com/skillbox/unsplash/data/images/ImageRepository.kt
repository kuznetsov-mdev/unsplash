package com.skillbox.unsplash.data.images

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.work.WorkInfo
import com.skillbox.unsplash.common.extensions.toImageItem
import com.skillbox.unsplash.data.images.retrofit.RetrofitImageRepository
import com.skillbox.unsplash.data.images.room.RoomImageRepository
import com.skillbox.unsplash.data.images.room.paging.ImageRemoteMediator
import com.skillbox.unsplash.data.images.storage.DiskImageRepository
import com.skillbox.unsplash.feature.images.detail.model.ImageDetailUiModel
import com.skillbox.unsplash.feature.images.list.model.ImageWithUserUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class ImageRepository(
    private val context: Application,
    private val diskImageRepository: DiskImageRepository,
    private val roomImageRepository: RoomImageRepository,
    private val retrofitImageRepository: RetrofitImageRepository
) {
    @OptIn(ExperimentalPagingApi::class)
    fun search(query: String?): Flow<PagingData<ImageWithUserUiModel>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            remoteMediator = ImageRemoteMediator(query, roomImageRepository, retrofitImageRepository, diskImageRepository, context),
            pagingSourceFactory = { roomImageRepository.getPagingSource(query) }
        ).flow
            .map { pagingData ->
                pagingData.map { imageEntity -> imageEntity.toImageItem() }
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

    suspend fun removeImages() {
        roomImageRepository.clearAll()
        diskImageRepository.removeAllFromInternalStorage()
    }

    fun startImageSavingToGalleryWork(name: String, url: String): LiveData<WorkInfo> {
        return diskImageRepository.startImageSavingToExternalStorageWork(name, url)
    }

    private companion object {
        const val PAGE_SIZE = 10
    }
}