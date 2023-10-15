package com.skillbox.unsplash.data.images.retrofit

import android.app.Application
import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.images.retrofit.model.image.RemoteImage
import com.skillbox.unsplash.data.images.storage.ImageRemoteDataSource
import com.skillbox.unsplash.feature.images.detail.data.DetailImageItem
import com.skillbox.unsplash.feature.images.list.data.ImageItem
import com.skillbox.unsplash.util.toDetailImageItem
import com.skillbox.unsplash.util.toImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File

class RetrofitImageDataSource(private val network: Network, private val context: Application) : ImageRemoteDataSource {

    override suspend fun fetchImages(pageNumber: Int, pageSize: Int): List<ImageItem> {
        return withContext(Dispatchers.IO) {
            convertToImageItem(network.unsplashApi.getImages(pageNumber, pageSize))
        }
    }

    override suspend fun searchImages(searchQuery: String, pageNumber: Int, pageSize: Int): List<ImageItem> {
        return withContext(Dispatchers.IO) {
            convertToImageItem(network.unsplashApi.searchImages(searchQuery, pageNumber, pageSize).result)
        }
    }

    override suspend fun setLike(imageId: String) {
        withContext(Dispatchers.IO) {
            network.unsplashApi.setLike(imageId)
        }
    }

    override suspend fun removeLike(imageId: String) {
        withContext(Dispatchers.IO) {
            network.unsplashApi.removeLike(imageId)
        }
    }

    override suspend fun getImageDetailInfo(imageId: String): DetailImageItem {
        return withContext(Dispatchers.IO) {
            network.unsplashApi.getImageDetailInfo(imageId).toDetailImageItem(
                "stub",
                "stub"
            )
        }
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