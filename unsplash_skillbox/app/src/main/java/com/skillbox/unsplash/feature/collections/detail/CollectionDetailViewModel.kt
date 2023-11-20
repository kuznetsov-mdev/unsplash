package com.skillbox.unsplash.feature.collections.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.unsplash.common.network.ConnectivityStatus
import com.skillbox.unsplash.common.network.api.ConnectivityObserver
import com.skillbox.unsplash.data.common.SearchCondition
import com.skillbox.unsplash.data.images.ImageRepository
import com.skillbox.unsplash.feature.images.list.model.ImageWithUserUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(
    private val imagesRepository: ImageRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {
    private val collectionImagesStateFlow = MutableStateFlow<PagingData<ImageWithUserUiModel>>(PagingData.empty())

    val collectionImageList: StateFlow<PagingData<ImageWithUserUiModel>>
        get() = collectionImagesStateFlow

    val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = connectivityObserver.observe()

    fun searchImages(collectionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            imagesRepository.search(SearchCondition.CollectionImages(collectionId)).cachedIn(viewModelScope).collectLatest {
                collectionImagesStateFlow.value = it
            }
        }
    }

}