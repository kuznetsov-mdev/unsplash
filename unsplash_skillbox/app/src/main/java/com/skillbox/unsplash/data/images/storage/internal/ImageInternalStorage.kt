package com.skillbox.unsplash.data.images.storage.internal

interface ImageInternalStorage {
    suspend fun saveImage(id: String, uri: String, dir: String)

    suspend fun removeAll()

    suspend fun removeImages(images: List<String>)
}