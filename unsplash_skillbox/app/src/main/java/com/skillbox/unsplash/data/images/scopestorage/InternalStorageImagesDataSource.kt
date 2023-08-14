package com.skillbox.unsplash.data.images.scopestorage

import android.content.Context
import android.graphics.Bitmap
import com.bumptech.glide.Glide
import com.skillbox.unsplash.data.images.ImagesInternalStorageDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream


class InternalStorageImagesDataSource(
    private val context: Context
) : ImagesInternalStorageDataSource {

    override suspend fun saveImagePreview(imageId: String, imageUri: String) {
        saveImage(
            imageId,
            context.cacheDir.resolve("thumbnails/"),
            withContext(Dispatchers.IO) {
                Glide.with(context)
                    .asBitmap()
                    .load(imageUri) // sample image
                    .submit()
                    .get()
            }
        )
    }

    override suspend fun getImagePreview(imageId: String): String {
        return ""
    }

    override suspend fun saveUserAvatar(userId: String, imageUri: String) {
        saveImage(
            userId,
            context.cacheDir.resolve("avatars/"),
            withContext(Dispatchers.IO) {
                Glide.with(context)
                    .asBitmap()
                    .load(imageUri) // sample image
                    .submit()
                    .get()
            }
        )
    }

    override suspend fun getUserAvatar(userId: String): String {
        return ""
    }

    private fun saveImage(imageId: String, storageDir: File, image: Bitmap): String? {
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