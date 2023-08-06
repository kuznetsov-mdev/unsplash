package com.skillbox.unsplash.data.images

interface ImagesScopeStorageDataSource {

    fun saveImagePreview(imageId: String, imageUri: String)

    fun getImagePreview(imageId: String): String

    fun saveUserAvatar(userId: String, imageUri: String)

    fun getUserAvatar(userId: String): String
}