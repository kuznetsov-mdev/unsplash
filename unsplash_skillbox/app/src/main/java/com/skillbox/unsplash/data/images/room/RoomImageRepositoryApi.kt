package com.skillbox.unsplash.data.images.room

import androidx.paging.PagingSource
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithUserEntity
import com.skillbox.unsplash.data.user.room.model.UserEntity
import com.skillbox.unsplash.feature.images.list.model.ImageWithUserUiModel

interface RoomImageRepositoryApi {

    suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<ImageWithUserUiModel>

    suspend fun insertAll(images: List<ImageWithUserEntity>)

    suspend fun clear(users: List<UserEntity>)

    suspend fun refresh(query: String?, images: List<ImageWithUserEntity>)

    fun getPagingSource(query: String?): PagingSource<Int, ImageWithUserEntity>

}