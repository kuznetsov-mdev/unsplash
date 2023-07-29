package com.skillbox.unsplash.data.images

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.images.model.RemoteImage
import com.skillbox.unsplash.data.images.paging.ImagesPageLoader
import com.skillbox.unsplash.data.images.paging.ImagesPageSource
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import com.skillbox.unsplash.util.toImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RetrofitImagesRepositoryImpl @Inject constructor(
    private val network: Network
) : ImagesRepository {

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

    override fun getPagedImages(): Flow<PagingData<ImageItem>> {
        val loader: ImagesPageLoader = { pageIndex, pageSize ->
            getImageList(pageIndex, pageSize).map { it.toImageItem() }
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagesPageSource(loader) }
        ).flow
    }

    private suspend fun getImageList(pageNumber: Int, pageSize: Int): List<RemoteImage> {
        //Retrofit runs query on background thread
        return withContext(Dispatchers.IO) {
            network.unsplashApi.searchImages(pageNumber, pageSize)
        }
    }

    companion object {
        const val PAGE_SIZE = 20
    }

}