package com.skillbox.unsplash.data.images.room

import androidx.room.withTransaction
import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.images.ImagesLocalDataSource
import com.skillbox.unsplash.data.images.retrofit.model.RemoteImage
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity
import com.skillbox.unsplash.util.toAuthorEntity
import com.skillbox.unsplash.util.toImageEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomImagesDataSource(private val dataBase: UnsplashRoomDataBase) : ImagesLocalDataSource {

    override suspend fun loadAllImages(): List<ImageWithAuthorEntity> {
        return withContext(Dispatchers.IO) {
            dataBase.imageDao().getImagesWithAuthor()
        }
    }

    override suspend fun saveRemoteImages(remoteImage: List<RemoteImage>) {
        withContext(Dispatchers.IO) {
            dataBase.withTransaction {
                dataBase.imageDao().insertAuthors(remoteImage.map { it.toAuthorEntity() })
                dataBase.imageDao().insertImages(remoteImage.map { it.toImageEntity() })
            }
        }
    }

    override suspend fun removeImages() {
        withContext(Dispatchers.IO) {
            dataBase.imageDao().deleteImages()
        }
    }
}