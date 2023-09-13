package com.skillbox.unsplash.data.images

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import com.skillbox.unsplash.data.images.retrofit.model.image.RemoteImage
import com.skillbox.unsplash.data.images.storage.ImageStorageDataSource
import com.skillbox.unsplash.data.images.storage.ImagesLocalDataSource
import com.skillbox.unsplash.data.images.storage.ImagesRemoteDataSource
import com.skillbox.unsplash.feature.images.detail.data.DetailImageItem
import com.skillbox.unsplash.feature.images.list.data.ImageItem
import com.skillbox.unsplash.util.toDetailImageItem
import com.skillbox.unsplash.util.toImageItem
import com.skillbox.unsplash.util.toImageWithAuthorEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import java.io.File


class ImagesRepository(
    private val context: Context,
    private val imageStorageDataSource: ImageStorageDataSource,
    private val imagesLocalDataSource: ImagesLocalDataSource,
    private val imagesRemoteDataSource: ImagesRemoteDataSource
) {
    suspend fun fetchImages(pageNumber: Int, pageSize: Int, isNetworkAvailable: Boolean): List<ImageItem> {
        return if (isNetworkAvailable) {
            Timber.d("Get images from Network")
            convertToImageItem(imagesRemoteDataSource.fetchImages(pageNumber, pageSize))
                .also { saveImageDataToLocalStorage(it) }
        } else {
            Timber.d("Get images from DB")
            imagesLocalDataSource.loadAllImages().map { it.toImageItem() }
        }
    }

    suspend fun getImageDetailInfo(imageId: String): DetailImageItem {
        return imagesRemoteDataSource.getImageDetailInfo(imageId).toDetailImageItem(
            "stub",
            "stub"
        )
    }

    suspend fun setLike(imageId: String) {
        imagesRemoteDataSource.setLike(imageId)
    }

    suspend fun removeLike(imageId: String) {
        imagesRemoteDataSource.removeLike(imageId)
    }

    suspend fun removeImages() {
        imagesLocalDataSource.removeImages()
        imageStorageDataSource.removeAllFromInternalStorage()
    }

    private suspend fun saveImageDataToLocalStorage(imageItemList: List<ImageItem>) {
        CoroutineScope(Dispatchers.IO).launch {
            imageItemList.forEach { saveImageToStorage(it) }
            imagesLocalDataSource.saveImages(imageItemList.map { it.toImageWithAuthorEntity() })
        }
    }

    private suspend fun saveImageToStorage(imageItem: ImageItem) {
        imageStorageDataSource.saveImageToInternalStorage(imageItem.image.id, imageItem.image.url, "thumbnails")
        imageStorageDataSource.saveImageToInternalStorage(imageItem.author.id, imageItem.author.avatarUrl, "avatars")
    }

    fun startImageSavingToGalleryWork(name: String, url: String): LiveData<WorkInfo> {
        return imageStorageDataSource.startImageSavingToExternalStorageWork(name, url)
    }

    private fun convertToImageItem(remoteImageList: List<RemoteImage>): List<ImageItem> {
        return remoteImageList.map { remoteImage ->
            runBlocking(Dispatchers.IO) {
                remoteImage.toImageItem(
                    File(context.cacheDir.path).resolve("thumbnails").resolve("${remoteImage.id}.jpg").toString(),
                    File(context.cacheDir.path).resolve("avatars").resolve("${remoteImage.user.id}.jpg").toString()
                )
            }
        }
    }
}