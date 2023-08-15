package com.skillbox.unsplash.data.images

interface ImagesInternalStorageDataSource {

    suspend fun saveImagePreview(imageId: String, imageUri: String)

    suspend fun saveUserAvatar(userId: String, imageUri: String)

    suspend fun clearAllImages()
}