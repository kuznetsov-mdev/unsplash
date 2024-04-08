package com.skillbox.unsplash.domain.api.storage

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.work.WorkInfo

interface ImageExternalStorage {
    suspend fun saveImage(name: String, url: String): Uri

    fun startSavingWork(name: String, url: String): LiveData<WorkInfo>

    suspend fun remove(uri: Uri)
}