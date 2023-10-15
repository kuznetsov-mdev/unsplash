package com.skillbox.unsplash.data.images.storage

import androidx.paging.PagingSource
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity
import com.skillbox.unsplash.feature.images.list.data.ImageItem

interface RoomImageRepository {

    suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<ImageItem>

    suspend fun searchImages(searchQuery: String, pageNumber: Int, pageSize: Int): List<ImageItem>

    suspend fun getPagingSource(query: String?): PagingSource<Int, ImageWithAuthorEntity>

    suspend fun saveImages(images: List<ImageWithAuthorEntity>)

    suspend fun removeImages()

}