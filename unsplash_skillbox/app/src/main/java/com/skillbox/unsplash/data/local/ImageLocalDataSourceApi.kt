package com.skillbox.unsplash.data.local

import androidx.paging.PagingSource
import com.skillbox.unsplash.common.SearchCondition
import com.skillbox.unsplash.data.local.db.entities.UserEntity
import com.skillbox.unsplash.data.local.db.entities.image.ImageWithUserEntity
import com.skillbox.unsplash.domain.model.ImageWithUserModel

interface ImageLocalDataSourceApi {

    suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<ImageWithUserModel>

    suspend fun insertAll(condition: SearchCondition, images: List<ImageWithUserEntity>)

    suspend fun clear(users: List<UserEntity>)

    suspend fun refresh(condition: SearchCondition, images: List<ImageWithUserEntity>)

    fun getPagingSource(searchCondition: SearchCondition): PagingSource<Int, ImageWithUserEntity>

    suspend fun clearAll();

    suspend fun clearAllUsers();

}