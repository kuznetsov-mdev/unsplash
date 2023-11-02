package com.skillbox.unsplash.data.images.retrofit

import com.skillbox.unsplash.data.images.retrofit.model.ImageRetrofitModel
import com.skillbox.unsplash.feature.images.detail.model.ImageDetailUiModel

interface RetrofitImageRepository {

    suspend fun getImages(searchQuery: String?, pageNumber: Int, pageSize: Int): List<ImageRetrofitModel>

    suspend fun setLike(imageId: String)

    suspend fun removeLike(imageId: String)

    suspend fun getImageDetailInfo(imageId: String): ImageDetailUiModel

}