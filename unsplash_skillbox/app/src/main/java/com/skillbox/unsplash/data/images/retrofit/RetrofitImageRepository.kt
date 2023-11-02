package com.skillbox.unsplash.data.images.retrofit

import com.skillbox.unsplash.data.model.retrofit.image.RetrofitImageModel
import com.skillbox.unsplash.feature.images.detail.model.ImageDetailUiModel

interface RetrofitImageRepository {

    suspend fun getImages(searchQuery: String?, pageNumber: Int, pageSize: Int): List<RetrofitImageModel>

    suspend fun setLike(imageId: String)

    suspend fun removeLike(imageId: String)

    suspend fun getImageDetailInfo(imageId: String): ImageDetailUiModel

}