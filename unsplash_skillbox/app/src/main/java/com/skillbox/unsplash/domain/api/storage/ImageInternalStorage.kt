package com.skillbox.unsplash.domain.api.storage

interface ImageInternalStorage {
    suspend fun saveImage(id: String, uri: String, dir: String)

    suspend fun removeAll()

    suspend fun removeImages(images: List<String>)
}