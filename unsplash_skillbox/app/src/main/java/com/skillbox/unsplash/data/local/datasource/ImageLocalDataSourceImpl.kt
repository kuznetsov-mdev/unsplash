package com.skillbox.unsplash.data.local.datasource

import androidx.paging.PagingSource
import androidx.room.withTransaction
import com.skillbox.unsplash.common.SearchCondition
import com.skillbox.unsplash.common.extensions.toImageUiModel
import com.skillbox.unsplash.data.local.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.local.db.entities.UserEntity
import com.skillbox.unsplash.data.local.db.entities.image.ImageWithUserEntity
import com.skillbox.unsplash.domain.model.ImageWithUserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ImageLocalDataSourceImpl(private val dataBase: UnsplashRoomDataBase) : ImageLocalDataSourceApi {

    override suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<ImageWithUserModel> {
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
                dataBase.imageDao().clearAll()
                dataBase.userDao().deleteUsers(users)
            }
        }
    }

    override suspend fun refresh(condition: SearchCondition, images: List<ImageWithUserEntity>) {
        withContext(Dispatchers.IO) {
            dataBase.withTransaction {
                when (condition) {
                    is SearchCondition.AllImages -> clear(images.map { it.user })
                    is SearchCondition.SearchQueryImages -> dataBase.imageDao().clearImages(condition.searchQuery)
                    is SearchCondition.UserImages -> Unit
                    is SearchCondition.LikedByUserImages -> Unit
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
            is SearchCondition.AllImages -> dataBase.imageDao().getImagesPagingSource()
            is SearchCondition.SearchQueryImages -> dataBase.imageDao().getImagesPagingSource(condition.searchQuery)
            is SearchCondition.CollectionImages -> dataBase.imageDao().getCollectionImagesPagingSource(condition.collectionId)
            is SearchCondition.UserImages -> dataBase.imageDao().getUserImages(condition.userName)
            is SearchCondition.LikedByUserImages -> dataBase.imageDao().getLikedUserImages()
            else -> error("Not implemented yet")
        }
    }

    override suspend fun clearAll() {
        dataBase.imageDao().clearAll()
    }

    override suspend fun clearAllUsers() {
        dataBase.userDao().clearAll()
    }
}