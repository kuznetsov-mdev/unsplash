package com.skillbox.unsplash.data.images.storage

import com.skillbox.unsplash.feature.images.detail.data.DetailImageItem

interface ImageRemoteDataSource : ImageDataSource {

    suspend fun setLike(imageId: String)

    suspend fun removeLike(imageId: String)

    suspend fun getImageDetailInfo(imageId: String): DetailImageItem

}