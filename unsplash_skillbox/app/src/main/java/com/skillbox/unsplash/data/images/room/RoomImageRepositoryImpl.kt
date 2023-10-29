package com.skillbox.unsplash.data.images.room

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import com.skillbox.unsplash.common.extensions.toImageItem
import com.skillbox.unsplash.data.model.room.relations.RoomImageWithUserModel
import com.skillbox.unsplash.feature.images.list.model.UiImageWithUserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomImageRepositoryImpl(private val dataBase: UnsplashRoomDataBase) : RoomImageRepository {

    override suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<UiImageWithUserModel> {
        return withContext(Dispatchers.IO) {
            dataBase.imageDao().getImagesWithAuthor().map { it.toImageItem() }
        }
    }

    override suspend fun insertAll(images: List<RoomImageWithUserModel>) {
        withContext(Dispatchers.IO) {
            dataBase.withTransaction {
                dataBase.imageDao().insertAuthors(images.map { it.author })
                dataBase.imageDao().insertImages(images.map { it.image })
            }
        }
    }

    override suspend fun clearAll() {
        withContext(Dispatchers.IO) {
            dataBase.withTransaction {
                dataBase.imageDao().deleteImages()
                dataBase.imageDao().deleteAuthors()
            }
        }
    }

    override suspend fun refresh(query: String?, images: List<RoomImageWithUserModel>) {
        withContext(Dispatchers.IO) {
            dataBase.withTransaction {
                if (query == null) {
                    clearAll()
                } else {
                    dataBase.imageDao().clear(query)
                }
                dataBase.imageDao().insertAuthors(images.map { it.author })
                dataBase.imageDao().insertImages(images.map { it.image })
            }
        }
    }

    override fun getPagingSource(query: String?): PagingSource<Int, RoomImageWithUserModel> {
        return if (query == null) {
            dataBase.imageDao().getPagingSource()
        } else {
            dataBase.imageDao().getPagingSource(query)
        }
    }
}