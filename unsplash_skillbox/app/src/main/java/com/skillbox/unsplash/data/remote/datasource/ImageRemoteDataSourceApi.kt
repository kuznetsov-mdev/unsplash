package com.skillbox.unsplash.data.remote.datasource

import com.skillbox.unsplash.common.UnsplashResponse
import com.skillbox.unsplash.data.remote.dto.ImageDto
import com.skillbox.unsplash.domain.model.detail.ImageDetailModel

interface ImageRemoteDataSourceApi {

    suspend fun getImages(pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

    suspend fun searchImages(searchQuery: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

    suspend fun getCollectionImages(collectionId: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

    suspend fun setLike(imageId: String)

    suspend fun removeLike(imageId: String)

    suspend fun getImageDetailInfo(imageId: String): ImageDetailModel

    suspend fun getUserImages(userName: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

    suspend fun getLikedUserImages(userName: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

}