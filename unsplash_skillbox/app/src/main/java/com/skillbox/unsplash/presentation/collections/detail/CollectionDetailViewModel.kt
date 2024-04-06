package com.skillbox.unsplash.presentation.collections.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.unsplash.data.common.SearchCondition
import com.skillbox.unsplash.data.impl.ImageRepository
import com.skillbox.unsplash.data.remote.network.ConnectivityObserver
import com.skillbox.unsplash.data.remote.network.ConnectivityStatus
import com.skillbox.unsplash.domain.model.local.ImageWithUserModel
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
    private val collectionImagesStateFlow = MutableStateFlow<PagingData<ImageWithUserModel>>(PagingData.empty())

    val collectionImageList: StateFlow<PagingData<ImageWithUserModel>>
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