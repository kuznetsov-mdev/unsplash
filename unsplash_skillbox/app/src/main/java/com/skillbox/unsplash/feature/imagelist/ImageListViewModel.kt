package com.skillbox.unsplash.feature.imagelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.unsplash.common.network.ConnectivityStatus
import com.skillbox.unsplash.common.network.api.ConnectivityObserver
import com.skillbox.unsplash.data.images.ImagesRepository
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import com.skillbox.unsplash.feature.imagelist.paging.ImagesPageLoader
import com.skillbox.unsplash.feature.imagelist.paging.ImagesPageSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImagesRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val imagesStateFlow: StateFlow<PagingData<ImageItem>> = getPagedImages()
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    var isNetworkAvailableState = true

    val imageList: StateFlow<PagingData<ImageItem>>
        get() = imagesStateFlow

    private val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = connectivityObserver.observe()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.removeImages()
        }
        observeConnectivityState()
    }

    fun setLike(imageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.setLike(imageId)
            } catch (t: Throwable) {
                Timber.d("Something wrong when try set like")
            }
        }
    }

    fun removeLike(imageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.removeLike(imageId)
            } catch (t: Throwable) {
                Timber.d("Something wrong when try remove like")
            }
        }
    }

    private fun getPagedImages(): Flow<PagingData<ImageItem>> {
        val loader: ImagesPageLoader = { pageIndex, pageSize ->
            repository.fetchImages(pageIndex, pageSize, isNetworkAvailableState)
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

    private fun observeConnectivityState() {
        viewModelScope.launch(Dispatchers.IO) {
            connectivityStateFlow.collectLatest {
                isNetworkAvailableState = it.name == ConnectivityStatus.Available.name
            }
        }
    }
}