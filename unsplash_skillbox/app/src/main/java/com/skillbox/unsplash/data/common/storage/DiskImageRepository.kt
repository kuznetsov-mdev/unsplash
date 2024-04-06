package com.skillbox.unsplash.data.common.storage

import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import com.skillbox.unsplash.domain.api.storage.ImageExternalStorage
import com.skillbox.unsplash.domain.api.storage.ImageInternalStorage
import javax.inject.Inject

class DiskImageRepository @Inject constructor(
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

    suspend fun removeCachedImages(images: List<String>) {
        internalStorage.removeImages(images)
    }

}