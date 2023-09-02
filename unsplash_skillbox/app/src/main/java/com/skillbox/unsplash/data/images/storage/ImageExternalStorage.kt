package com.skillbox.unsplash.data.images.storage

import android.net.Uri

interface ImageExternalStorage {

    suspend fun saveImage(name: String, url: String)

    suspend fun remove(uri: Uri)
}