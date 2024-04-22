package com.skillbox.unsplash.domain.api.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import androidx.work.WorkInfo
import com.skillbox.unsplash.common.SearchCondition
import com.skillbox.unsplash.data.local.db.entities.image.ImageWithUserEntity
import com.skillbox.unsplash.domain.model.detail.ImageDetailModel
import kotlinx.coroutines.flow.Flow

interface ImageRepositoryApi {

    fun search(searchCondition: SearchCondition): Flow<PagingData<ImageWithUserEntity>>

    suspend fun getImageDetailInfo(imageId: String): ImageDetailModel

    suspend fun likeImage(imageId: String)

    suspend fun unlikeImage(imageId: String)

    fun startImageSavingToGalleryWork(name: String, url: String): LiveData<WorkInfo>

    suspend fun clearAllData()
}