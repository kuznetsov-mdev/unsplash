package com.skillbox.unsplash.data.images.room

import androidx.room.withTransaction
import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.images.ImagesLocalDataSource
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomImagesDataSource(private val dataBase: UnsplashRoomDataBase) : ImagesLocalDataSource {

    override suspend fun loadAllImages(): List<ImageWithAuthorEntity> {
        return withContext(Dispatchers.IO) {
            dataBase.imageDao().getImagesWithAuthor()
        }
    }

    override suspend fun saveImages(images: List<ImageWithAuthorEntity>) {
        withContext(Dispatchers.IO) {
            dataBase.withTransaction {
                dataBase.imageDao().insertAuthors(images.map { it.author })
                dataBase.imageDao().insertImages(images.map { it.image })
            }
        }
    }

    override suspend fun removeImages() {
        withContext(Dispatchers.IO) {
            dataBase.imageDao().deleteImages()
        }
    }
}