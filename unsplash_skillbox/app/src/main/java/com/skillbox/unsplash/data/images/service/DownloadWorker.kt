package com.skillbox.unsplash.data.images.service

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.skillbox.unsplash.data.images.storage.external.ImageExternalStorage
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class DownloadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    @Assisted private val imageExternalStorage: ImageExternalStorage
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val urlToDownload: String = inputData.getString(IMAGER_URL_KEY)!!
        val imageNameToSave: String = inputData.getString(IMAGE_NAME_KEY)!!

        return try {
            imageExternalStorage.saveImage(imageNameToSave, urlToDownload)
            Result.success()
        } catch (e: Throwable) {
            Result.failure()
        }
    }

    companion object {
        const val IMAGER_URL_KEY = "image url"
        const val IMAGE_NAME_KEY = "image name"

    }

}