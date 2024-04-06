package com.skillbox.unsplash.domain.api.repository

import androidx.paging.PagingSource
import com.skillbox.unsplash.data.common.SearchCondition
import com.skillbox.unsplash.domain.model.db.UserEntity
import com.skillbox.unsplash.domain.model.db.image.ImageWithUserEntity
import com.skillbox.unsplash.domain.model.local.ImageWithUserModel

interface RoomImageRepositoryApi {

    suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<ImageWithUserModel>

    suspend fun insertAll(condition: SearchCondition, images: List<ImageWithUserEntity>)

    suspend fun clear(users: List<UserEntity>)

    suspend fun refresh(condition: SearchCondition, images: List<ImageWithUserEntity>)

    fun getPagingSource(searchCondition: SearchCondition): PagingSource<Int, ImageWithUserEntity>

    suspend fun clearAll();

    suspend fun clearAllUsers();

}