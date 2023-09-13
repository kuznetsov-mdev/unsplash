package com.skillbox.unsplash.data.images.service

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.skillbox.unsplash.data.images.storage.external.ImageExternalStorage
import javax.inject.Inject

class DownloadFactory @Inject constructor(
    private val imageExternalStorage: ImageExternalStorage
) : WorkerFactory() {

    override fun createWorker(appContext: Context, workerClassName: String, workerParameters: WorkerParameters): ListenableWorker? {
        return DownloadWorker(appContext, workerParameters, imageExternalStorage)
    }
}