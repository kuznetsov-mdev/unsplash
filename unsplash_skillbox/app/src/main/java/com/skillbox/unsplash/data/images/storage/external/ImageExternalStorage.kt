package com.skillbox.unsplash.data.images.storage.external

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.work.WorkInfo

interface ImageExternalStorage {
    suspend fun saveImage(name: String, url: String)

    fun startSavingWork(name: String, url: String): LiveData<WorkInfo>

    suspend fun remove(uri: Uri)
}