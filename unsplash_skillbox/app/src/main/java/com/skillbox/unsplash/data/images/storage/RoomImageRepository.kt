package com.skillbox.unsplash.data.images.storage

import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity

interface RoomImageRepository : ImageDataSource {

    suspend fun saveImages(images: List<ImageWithAuthorEntity>)

    suspend fun removeImages()

}