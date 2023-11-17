package com.skillbox.unsplash.data.images.room

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import com.skillbox.unsplash.common.extensions.toImageUiModel
import com.skillbox.unsplash.data.common.SearchCondition
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithUserEntity
import com.skillbox.unsplash.data.user.room.model.UserEntity
import com.skillbox.unsplash.feature.images.list.model.ImageWithUserUiModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomImageRepositoryImpl(private val dataBase: UnsplashRoomDataBase) : RoomImageRepositoryApi {

    override suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<ImageWithUserUiModel> {
        return withContext(Dispatchers.IO) {
            dataBase.imageDao().getImagesWithUser().map { it.toImageUiModel() }
        }
    }

    override suspend fun insertAll(condition: SearchCondition, images: List<ImageWithUserEntity>) {
        withContext(Dispatchers.IO) {
            dataBase.withTransaction {
                if (condition is SearchCondition.CollectionImages) {
                    images.forEach { img ->
                        dataBase.collectionImageDao().insertCollectionImages(condition.collectionId, img.image.id)
                    }
                }
                dataBase.userDao().insertUsers(images.map { it.user })
                dataBase.imageDao().insertImages(images.map { it.image })
            }
        }
    }

    override suspend fun clear(users: List<UserEntity>) {
        withContext(Dispatchers.IO) {
            dataBase.withTransaction {
                dataBase.imageDao().deleteImages()
                dataBase.userDao().deleteUsers(users)
            }
        }
    }

    override suspend fun refresh(condition: SearchCondition, images: List<ImageWithUserEntity>) {
        withContext(Dispatchers.IO) {
            dataBase.withTransaction {
                when (condition) {
                    is SearchCondition.Empty -> clear(images.map { it.user })
                    is SearchCondition.SearchString -> dataBase.imageDao().clearImages(condition.searchQuery)
                    is SearchCondition.CollectionImages -> {
                        dataBase.imageDao().clearCollectionImages(condition.collectionId)
                        dataBase.collectionImageDao().clearCollectionImages(condition.collectionId)
                        images.forEach { image ->
                            dataBase.collectionImageDao().insertCollectionImages(condition.collectionId, image.image.id)
                        }
                    }
                }

                dataBase.userDao().insertUsers(images.map { it.user })
                dataBase.imageDao().insertImages(images.map { it.image })
            }
        }
    }

    override fun getPagingSource(condition: SearchCondition): PagingSource<Int, ImageWithUserEntity> {
        return when (condition) {
            is SearchCondition.Empty -> dataBase.imageDao().getImagesPagingSource()
            is SearchCondition.SearchString -> dataBase.imageDao().getImagesPagingSource(condition.searchQuery)
            is SearchCondition.CollectionImages -> dataBase.imageDao().getCollectionImagesPagingSource(condition.collectionId)
            else -> error("Not implemented yet")
        }
    }
}