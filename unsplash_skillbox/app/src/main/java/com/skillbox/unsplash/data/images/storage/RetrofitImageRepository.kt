package com.skillbox.unsplash.data.images.storage

import com.skillbox.unsplash.feature.images.detail.data.DetailImageItem
import com.skillbox.unsplash.feature.images.list.data.ImageItem

interface RetrofitImageRepository : ImageDataSource {

    suspend fun setLike(imageId: String)

    suspend fun removeLike(imageId: String)

    suspend fun getImageDetailInfo(imageId: String): DetailImageItem

}