package com.skillbox.unsplash.data.images.storage

interface ImageInternalStorage {
    suspend fun saveImage(id: String, uri: String, dir: String)

    suspend fun removeAll()
}