package com.skillbox.unsplash.feature.imagelist.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

typealias  ImagesPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<ImageItem>

class ImagesPageSource(
    private val loader: ImagesPageLoader,
    private val pageSize: Int
) : PagingSource<Int, ImageItem>() {

    override fun getRefreshKey(state: PagingState<Int, ImageItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageItem> {
        return try {
            withContext(Dispatchers.IO) {
                val pageIndex = params.key ?: 0
                val imageItem = loader.invoke(pageIndex, params.loadSize)
                Timber.d("Loader was invoked on thread - ${Thread.currentThread().name}")
                val prevPageIndex = if (pageIndex == 0) null else pageIndex - 1
                val nexPageIndex = if (imageItem.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
                LoadResult.Page(imageItem, prevPageIndex, nexPageIndex)
            }
        } catch (t: Throwable) {
            LoadResult.Error(throwable = t)
        }
    }

}