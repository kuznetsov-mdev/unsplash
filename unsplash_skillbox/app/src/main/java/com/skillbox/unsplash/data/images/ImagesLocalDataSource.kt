package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.data.images.retrofit.model.RemoteImage
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity

interface ImagesLocalDataSource {

    suspend fun loadAllImages(): List<ImageWithAuthorEntity>

    suspend fun saveRemoteImages(images: List<RemoteImage>)
}