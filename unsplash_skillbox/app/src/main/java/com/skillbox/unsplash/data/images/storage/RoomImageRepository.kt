package com.skillbox.unsplash.data.images.storage

import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity
import com.skillbox.unsplash.feature.images.list.data.ImageItem

interface RoomImageRepository : ImageDataSource {

    suspend fun saveImages(images: List<ImageWithAuthorEntity>)

    suspend fun removeImages()

}