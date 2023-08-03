package com.skillbox.unsplash.feature.imagelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.unsplash.data.images.ImagesRepository
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import com.skillbox.unsplash.feature.imagelist.paging.ImagesPageLoader
import com.skillbox.unsplash.feature.imagelist.paging.ImagesPageSource
import com.skillbox.unsplash.util.toImageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImagesRepository
) : ViewModel() {

    private val imagesStateFlow: StateFlow<PagingData<ImageItem>> = getPagedImages()
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    val imageList: StateFlow<PagingData<ImageItem>>
        get() = imagesStateFlow

    fun setLike(imageId: String) {
        viewModelScope.launch {
            try {
                repository.setLike(imageId)
            } catch (t: Throwable) {
                Timber.d("Something wrong")
            }
        }
    }

    fun removeLike(imageId: String) {
        viewModelScope.launch {
            try {
                repository.removeLike(imageId)
            } catch (t: Throwable) {
                Timber.d("Something wrong")
            }
        }
    }

    private fun getPagedImages(): Flow<PagingData<ImageItem>> {
        val loader: ImagesPageLoader = { pageIndex, pageSize ->
            repository.fetchImages(pageIndex, pageSize).map { it.toImageItem() }
        }
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = true
            ),
            pagingSourceFactory = { ImagesPageSource(loader, PAGE_SIZE) }
        ).flow.cachedIn(viewModelScope)
    }

    private companion object {
        const val PAGE_SIZE = 10
    }
}