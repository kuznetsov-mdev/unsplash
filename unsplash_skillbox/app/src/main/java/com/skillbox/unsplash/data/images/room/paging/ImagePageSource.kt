package com.skillbox.unsplash.data.images.room.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skillbox.unsplash.data.images.storage.RetrofitImageRepository
import com.skillbox.unsplash.feature.images.list.data.ImageItem
import com.skillbox.unsplash.util.toImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ImagePageSource(
    private val retrofitImageRepository: RetrofitImageRepository,
    private val query: String,
    private val pageSize: Int,
) : PagingSource<Int, ImageItem>() {

    override fun getRefreshKey(state: PagingState<Int, ImageItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageItem> {
        return try {
            withContext(Dispatchers.IO) {
                val pageIndex: Int = params.key ?: 0
                val imageUiEntities: List<ImageItem> = if (query.isNotBlank()) {
                    retrofitImageRepository.searchImages(query, pageIndex, params.loadSize).map { it.toImageItem("", "") }
                } else {
                    retrofitImageRepository.fetchImages(pageIndex, params.loadSize).map { it.toImageItem("", "") }
                }
                Timber.d("Loader was invoked on thread - ${Thread.currentThread().name}")
                val prevPageIndex: Int? = if (pageIndex == 0) null else pageIndex - 1
                val nexPageIndex: Int? = if (imageUiEntities.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
                LoadResult.Page(imageUiEntities, prevPageIndex, nexPageIndex)
            }
        } catch (t: Throwable) {
            LoadResult.Error(throwable = t)
        }
    }
}