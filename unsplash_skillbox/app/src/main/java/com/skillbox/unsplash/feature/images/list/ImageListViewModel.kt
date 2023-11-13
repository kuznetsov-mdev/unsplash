package com.skillbox.unsplash.feature.images.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.unsplash.common.network.ConnectivityStatus
import com.skillbox.unsplash.common.network.api.ConnectivityObserver
import com.skillbox.unsplash.data.images.ImageRepository
import com.skillbox.unsplash.feature.images.list.model.ImageWithUserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val imagesStateFlow = MutableStateFlow<PagingData<ImageWithUserUiModel>>(PagingData.empty())

    var isNetworkAvailableState = true

    val imageList: StateFlow<PagingData<ImageWithUserUiModel>>
        get() = imagesStateFlow

    private val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = connectivityObserver.observe()

    init {
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

    fun searchImages(searchQuery: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.search(searchQuery).cachedIn(viewModelScope).collectLatest {
                imagesStateFlow.value = it
            }
        }
    }

    private fun observeConnectivityState() {
        viewModelScope.launch(Dispatchers.IO) {
            connectivityStateFlow.collectLatest {
                isNetworkAvailableState = it.name == ConnectivityStatus.Available.name
            }
        }
    }
}