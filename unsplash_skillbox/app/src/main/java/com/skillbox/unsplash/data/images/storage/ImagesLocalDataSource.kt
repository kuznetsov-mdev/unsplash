package com.skillbox.unsplash.data.images.storage

import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity

interface ImagesLocalDataSource : ImageDataSource {

    suspend fun saveImages(images: List<ImageWithAuthorEntity>)

    suspend fun removeImages()

}