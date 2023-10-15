package com.skillbox.unsplash.data.images.storage

interface ImageDataSource {

    suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<ImageItem>

    suspend fun searchImages(searchQuery: String, pageNumber: Int, pageSize: Int): List<ImageItem>
}