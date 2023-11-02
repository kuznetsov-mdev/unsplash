package com.skillbox.unsplash.data.images.room

import androidx.paging.PagingSource
import com.skillbox.unsplash.data.model.room.relations.RoomImageWithUserModel
import com.skillbox.unsplash.feature.images.list.model.ImageWithUserUiModel

interface RoomImageRepository {

    suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<ImageWithUserUiModel>

    suspend fun insertAll(images: List<RoomImageWithUserModel>)

    suspend fun clearAll()

    suspend fun refresh(query: String?, images: List<RoomImageWithUserModel>)

    fun getPagingSource(query: String?): PagingSource<Int, RoomImageWithUserModel>

}