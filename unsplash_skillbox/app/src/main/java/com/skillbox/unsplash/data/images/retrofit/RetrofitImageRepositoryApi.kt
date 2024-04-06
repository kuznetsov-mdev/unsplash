package com.skillbox.unsplash.data.images.retrofit

import com.skillbox.unsplash.data.common.retrofit.UnsplashResponse
import com.skillbox.unsplash.data.images.retrofit.model.ImageDto
import com.skillbox.unsplash.domain.model.local.detail.ImageDetailUiModel

interface RetrofitImageRepositoryApi {

    suspend fun getImages(pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

    suspend fun searchImages(searchQuery: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

    suspend fun getCollectionImages(collectionId: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

    suspend fun setLike(imageId: String)

    suspend fun removeLike(imageId: String)

    suspend fun getImageDetailInfo(imageId: String): ImageDetailUiModel

    suspend fun getUserImages(userName: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

    suspend fun getLikedUserImages(userName: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

}