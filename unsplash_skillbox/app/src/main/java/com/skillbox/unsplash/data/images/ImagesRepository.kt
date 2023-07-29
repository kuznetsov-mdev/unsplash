package com.skillbox.unsplash.data.images

import androidx.paging.PagingData
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import kotlinx.coroutines.flow.Flow

interface ImagesRepository {

    suspend fun setLike(imageId: String)

    suspend fun removeLike(imageId: String)

    fun getPagedImages(): Flow<PagingData<ImageItem>>
}