package com.skillbox.unsplash.data.images.room

import com.skillbox.unsplash.data.images.ImagesLocalDataSource
import com.skillbox.unsplash.data.images.retrofit.model.RemoteImage
import com.skillbox.unsplash.data.images.room.dao.ImageDao
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomImagesDataSource(private val imageDao: ImageDao) : ImagesLocalDataSource {

    override suspend fun loadAllImages(): List<ImageWithAuthorEntity> {
        return withContext(Dispatchers.IO) {
            imageDao.getImagesWithAuthor()
        }
    }

    override suspend fun saveRemoteImages(images: List<RemoteImage>) {
        withContext(Dispatchers.IO) {
            imageDao.insertImagesWithAuthor(images)
        }
    }

}