package com.skillbox.unsplash.data.images.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.skillbox.unsplash.feature.imagelist.data.ImageItem

typealias ImagesPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<ImageItem>

class ImagesPageSource(
    private val loader: ImagesPageLoader
) : PagingSource<Int, ImageItem>() {

    override fun getRefreshKey(state: PagingState<Int, ImageItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null;
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageItem> {
        return try {
            val pageIndex = params.key ?: 0
            val pageSize = params.loadSize
            val imageItem = loader.invoke(pageIndex, pageSize)
            val nexPageNumber = if (imageItem.size < pageSize) null else pageIndex + 1
            val prevPageNumber = if (pageIndex == 1) null else pageIndex - 1

            LoadResult.Page(imageItem, prevPageNumber, nexPageNumber)
        } catch (t: Throwable) {
            LoadResult.Error(throwable = t)
        }
    }

}