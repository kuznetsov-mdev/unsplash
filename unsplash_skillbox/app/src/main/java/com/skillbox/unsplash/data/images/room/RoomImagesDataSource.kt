package com.skillbox.unsplash.data.images.room

import androidx.room.withTransaction
import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity
import com.skillbox.unsplash.data.images.storage.ImagesLocalDataSource
import com.skillbox.unsplash.feature.images.list.data.ImageItem
import com.skillbox.unsplash.util.toImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomImagesDataSource(private val dataBase: UnsplashRoomDataBase) : ImagesLocalDataSource {

    override suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<ImageItem> {
        return withContext(Dispatchers.IO) {
            dataBase.imageDao().getImagesWithAuthor().map { it.toImageItem() }
        }
    }

    override suspend fun searchImages(searchQuery: String, pageNumber: Int, pageSize: Int): List<ImageItem> {
        return dataBase.imageDao().searchImages(searchQuery, pageNumber, pageSize).map { it.toImageItem() }
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
            dataBase.withTransaction {
                dataBase.imageDao().deleteImages()
                dataBase.imageDao().deleteAuthorsAvatars()
            }
        }
    }
}