package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.data.images.model.RemoteImage

interface ImagesRepository {

    suspend fun setLike(imageId: String)

    suspend fun removeLike(imageId: String)

    suspend fun getImageList(pageNumber: Int, pageSize: Int): List<RemoteImage>
}