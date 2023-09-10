package com.skillbox.unsplash.data.images.storage.internal

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.images.storage.ImageExternalStorage
import com.skillbox.unsplash.util.haveQ
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageExternalStorageImpl @Inject constructor(
    private val context: Context,
    private val network: Network,
) : ImageExternalStorage {

    @RequiresApi(Build.VERSION_CODES.Q)
    override suspend fun saveImage(name: String, url: String) {
        withContext(Dispatchers.IO) {
            val saveImageUri = saveImageInfo(name)
            try {
                downloadImage(url, saveImageUri)
                makeImageVisible(saveImageUri)
            } catch (t: Throwable) {
                remove(saveImageUri)
            }
        }
    }

    override suspend fun remove(uri: Uri) {
        withContext(Dispatchers.IO) {
            context.contentResolver.delete(uri, null, null)
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun saveImageInfo(name: String): Uri {
        val volume = if (haveQ()) {
            MediaStore.VOLUME_EXTERNAL_PRIMARY
        } else {
            MediaStore.VOLUME_EXTERNAL
        }

        val imageCollectionUri = MediaStore.Images.Media.getContentUri(volume)
        val imageInfo = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, name)
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)

            if (haveQ()) {
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }
        return context.contentResolver.insert(imageCollectionUri, imageInfo)!!
    }

    private suspend fun downloadImage(url: String, uri: Uri) {
        context.contentResolver.openOutputStream(uri)?.use { outStream ->
            network.uploaderApi
                .getFile(url)
                .byteStream()
                .use { inStream ->
                    inStream.copyTo(outStream)
                }
        }
    }

    private fun makeImageVisible(uri: Uri) {
        if (haveQ().not()) return

        val imageInfo = ContentValues().apply {
            put(MediaStore.Images.Media.IS_PENDING, 0)
        }
        context.contentResolver.update(uri, imageInfo, null, null)
    }
}