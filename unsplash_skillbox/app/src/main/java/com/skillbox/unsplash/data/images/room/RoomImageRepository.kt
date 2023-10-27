package com.skillbox.unsplash.data.images.room

import androidx.paging.PagingSource
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity
import com.skillbox.unsplash.feature.images.list.data.ImageItem

interface RoomImageRepository {

    suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<ImageItem>

    suspend fun insertAll(images: List<ImageWithAuthorEntity>)

    suspend fun clearAll()

    suspend fun refresh(query: String?, images: List<ImageWithAuthorEntity>)

    fun getPagingSource(query: String?): PagingSource<Int, ImageWithAuthorEntity>

}