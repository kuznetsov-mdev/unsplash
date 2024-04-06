package com.skillbox.unsplash.data.local.storage.external

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.skillbox.unsplash.domain.api.storage.ImageInternalStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import javax.inject.Inject

class ImageInternalStorageImpl @Inject constructor(
    private val context: Context
) : ImageInternalStorage {

    override suspend fun saveImage(id: String, url: String, dir: String) {
        saveImageToDir(
            id,
            context.cacheDir.resolve(dir),
            withContext(Dispatchers.IO) {
                Glide.with(context)
                    .asBitmap()
                    .load(url) // sample image
                    .submit()
                    .get()
            }
        )
    }

    override suspend fun removeAll() {
        if (context.cacheDir.resolve("avatars").exists()) {
            val isAvatarsRemoved = File(context.cacheDir, "avatars").deleteRecursively()
            Timber.d("Is avatar removed = $isAvatarsRemoved")
        }

        val isImagesRemoved = File(context.cacheDir, "thumbnails").deleteRecursively()
        Timber.d("Is images removed = $isImagesRemoved")
    }


    override suspend fun removeImages(images: List<String>) {
        images.forEach { imageLink ->
            File(imageLink).deleteOnExit()
        }
    }

    private fun saveImageToDir(imageId: String, storageDir: File, image: Bitmap): String? {
        var savedImagePath: String? = null
        val imageFileName = "$imageId.jpg"
        var success = true
        if (!storageDir.exists()) {
            success = storageDir.mkdirs()
        }
        if (success) {
            val imageFile = File(storageDir, imageFileName)
            savedImagePath = imageFile.getAbsolutePath()
            try {
                val fOut: OutputStream = FileOutputStream(imageFile)
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut)
                fOut.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return savedImagePath
    }
}