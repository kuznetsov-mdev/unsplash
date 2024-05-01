package com.skillbox.unsplash.data.remote

import com.skillbox.unsplash.common.LoadState
import com.skillbox.unsplash.common.UnsplashResponse
import com.skillbox.unsplash.data.remote.dto.ImageDto
import com.skillbox.unsplash.data.remote.dto.image.ImageDetailDto

interface ImageRemoteDataSourceApi {

    suspend fun getImages(pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

    suspend fun searchImages(searchQuery: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

    suspend fun getCollectionImages(collectionId: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

    suspend fun setLike(imageId: String)

    suspend fun removeLike(imageId: String)

    suspend fun getImageDetailInfo(imageId: String): LoadState<ImageDetailDto>

    suspend fun getUserImages(userName: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

    suspend fun getLikedUserImages(userName: String, pageNumber: Int, pageSize: Int): UnsplashResponse<List<ImageDto>>

}