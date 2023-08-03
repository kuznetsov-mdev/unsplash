package com.skillbox.unsplash.data.images.room

import com.skillbox.unsplash.data.images.ImagesLocalDataSource
import com.skillbox.unsplash.data.images.room.dao.ImageDao
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity

class RoomImagesDataSource(private val imageDao: ImageDao) : ImagesLocalDataSource {

    override suspend fun loadAllImages(): List<ImageWithAuthorEntity> {
        return imageDao.getImagesWithAuthor()
    }


}