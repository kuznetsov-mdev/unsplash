package com.skillbox.unsplash.data.images.room

import androidx.paging.PagingSource
import com.skillbox.unsplash.data.images.room.model.UserRoomModel
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithUserRoomModel
import com.skillbox.unsplash.feature.images.list.model.ImageWithUserUiModel

interface RoomImageRepository {

    suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<ImageWithUserUiModel>

    suspend fun insertAll(images: List<ImageWithUserRoomModel>)

    suspend fun clear(users: List<UserRoomModel>)

    suspend fun refresh(query: String?, images: List<ImageWithUserRoomModel>)

    fun getPagingSource(query: String?): PagingSource<Int, ImageWithUserRoomModel>

}