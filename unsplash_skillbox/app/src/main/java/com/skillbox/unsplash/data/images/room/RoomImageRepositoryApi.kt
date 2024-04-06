package com.skillbox.unsplash.data.images.room

import androidx.paging.PagingSource
import com.skillbox.unsplash.data.common.SearchCondition
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithUserEntity
import com.skillbox.unsplash.data.user.room.model.UserEntity
import com.skillbox.unsplash.domain.model.local.ImageWithUserUiModel

interface RoomImageRepositoryApi {

    suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<ImageWithUserUiModel>

    suspend fun insertAll(condition: SearchCondition, images: List<ImageWithUserEntity>)

    suspend fun clear(users: List<UserEntity>)

    suspend fun refresh(condition: SearchCondition, images: List<ImageWithUserEntity>)

    fun getPagingSource(searchCondition: SearchCondition): PagingSource<Int, ImageWithUserEntity>

    suspend fun clearAll();

    suspend fun clearAllUsers();

}