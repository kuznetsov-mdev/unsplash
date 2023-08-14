package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity

interface ImagesLocalDataSource {

    suspend fun loadAllImages(): List<ImageWithAuthorEntity>

    suspend fun saveImages(images: List<ImageWithAuthorEntity>)

    suspend fun removeImages()
}