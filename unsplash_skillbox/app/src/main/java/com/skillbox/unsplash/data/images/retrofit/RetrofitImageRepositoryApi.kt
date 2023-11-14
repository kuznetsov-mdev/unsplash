package com.skillbox.unsplash.data.images.retrofit

import com.skillbox.unsplash.data.images.retrofit.model.ImageDto
import com.skillbox.unsplash.feature.images.detail.model.ImageDetailUiModel

interface RetrofitImageRepositoryApi {

    suspend fun getImages(searchQuery: String?, pageNumber: Int, pageSize: Int): List<ImageDto>

    suspend fun setLike(imageId: String)

    suspend fun removeLike(imageId: String)

    suspend fun getImageDetailInfo(imageId: String): ImageDetailUiModel

}