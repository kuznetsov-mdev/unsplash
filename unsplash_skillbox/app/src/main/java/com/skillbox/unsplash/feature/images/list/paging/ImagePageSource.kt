package com.skillbox.unsplash.feature.images.list.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skillbox.unsplash.data.images.storage.ImageDataSource
import com.skillbox.unsplash.feature.images.list.data.ImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class ImagePageSource(
    private val imageDataSource: ImageDataSource,
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
                val imageItems: List<ImageItem> = if (query.isNotBlank()) {
                    imageDataSource.searchImages(query, pageIndex, params.loadSize)
                } else {
                    imageDataSource.fetchImages(pageIndex, params.loadSize)
                }
                Timber.d("Loader was invoked on thread - ${Thread.currentThread().name}")
                val prevPageIndex: Int? = if (pageIndex == 0) null else pageIndex - 1
                val nexPageIndex: Int? = if (imageItems.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
                LoadResult.Page(imageItems, prevPageIndex, nexPageIndex)
            }
        } catch (t: Throwable) {
            LoadResult.Error(throwable = t)
        }
    }
}