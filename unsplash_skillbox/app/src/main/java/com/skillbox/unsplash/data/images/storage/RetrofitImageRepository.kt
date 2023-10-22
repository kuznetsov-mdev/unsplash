package com.skillbox.unsplash.data.images.storage

import com.skillbox.unsplash.data.images.retrofit.model.image.RemoteImage
import com.skillbox.unsplash.feature.images.detail.data.DetailImageItem

interface RetrofitImageRepository {

    suspend fun getImages(searchQuery: String?, pageNumber: Int, pageSize: Int): List<RemoteImage>

    suspend fun setLike(imageId: String)

    suspend fun removeLike(imageId: String)

    suspend fun getImageDetailInfo(imageId: String): DetailImageItem

}