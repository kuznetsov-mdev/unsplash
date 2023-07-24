package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.data.images.model.RemoteImage

interface ImageListRepositoryApi {

    suspend fun getImageList(imgsPerPage: Int): List<RemoteImage>

    suspend fun setLike(imageId: String)

    suspend fun removeLike(imageId: String)
}