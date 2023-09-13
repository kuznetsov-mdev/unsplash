package com.skillbox.unsplash.data.images.storage

import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import com.skillbox.unsplash.data.images.storage.external.ImageExternalStorage
import com.skillbox.unsplash.data.images.storage.internal.ImageInternalStorage
import javax.inject.Inject

class ImageStorageDataSource @Inject constructor(
    private val internalStorage: ImageInternalStorage,
    private val externalStorage: ImageExternalStorage
) {

    suspend fun saveImageToInternalStorage(id: String, uri: String, dir: String) {
        internalStorage.saveImage(id, uri, dir)
    }

    fun startImageSavingToExternalStorageWork(name: String, uri: String): LiveData<WorkInfo> {
        return externalStorage.startSavingWork(name, uri)
    }

    suspend fun removeAllFromInternalStorage() {
        internalStorage.removeAll()
    }

}