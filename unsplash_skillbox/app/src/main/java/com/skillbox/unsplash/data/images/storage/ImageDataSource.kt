package com.skillbox.unsplash.data.images.storage

import com.skillbox.unsplash.feature.images.list.data.ImageItem

interface ImageDataSource {

    suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<ImageItem>

    suspend fun searchImages(searchQuery: String, pageNumber: Int, pageSize: Int): List<ImageItem>
}