package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.data.images.storage.ImageExternalStorage
import com.skillbox.unsplash.data.images.storage.ImageInternalStorage
import javax.inject.Inject

class ImageStorageDataSource @Inject constructor(
    private val internalStorage: ImageInternalStorage,
    private val externalStorage: ImageExternalStorage
) {

    suspend fun saveImageToInternalStorage(id: String, uri: String, dir: String) {
        internalStorage.saveImage(id, uri, dir)
    }

    suspend fun saveImageToExternalStorage(name: String, uri: String) {
        externalStorage.saveImage(name, uri)
    }

    suspend fun removeAllFromInternalStorage() {
        internalStorage.removeAll()
    }

}