package com.skillbox.unsplash.feature.images.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.skillbox.unsplash.common.network.ConnectivityStatus
import com.skillbox.unsplash.common.network.api.ConnectivityObserver
import com.skillbox.unsplash.data.images.ImagesRepository
import com.skillbox.unsplash.feature.images.list.data.ImageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImagesRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val imagesStateFlow = MutableStateFlow<PagingData<ImageItem>>(PagingData.empty())

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
        searchImages("")
    }

    override fun onCleared() {
        super.onCleared()
        runBlocking(Dispatchers.IO) {
            repository.removeImages()
        }
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

    fun searchImages(searchQuery: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.search(searchQuery, PAGE_SIZE, isNetworkAvailableState).collectLatest {
                imagesStateFlow.value = it
            }
        }
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