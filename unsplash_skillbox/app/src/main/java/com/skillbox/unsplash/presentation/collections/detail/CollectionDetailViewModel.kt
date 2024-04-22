package com.skillbox.unsplash.presentation.collections.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.unsplash.common.SearchCondition
import com.skillbox.unsplash.data.remote.network.ConnectivityStatus
import com.skillbox.unsplash.domain.model.ImageWithUserModel
import com.skillbox.unsplash.domain.usecase.common.GetNetworkStateUseCase
import com.skillbox.unsplash.domain.usecase.image.GetImagesUseCase
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
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val searchImagesUseCase: GetImagesUseCase
) : ViewModel() {
    private val collectionImagesStateFlow = MutableStateFlow<PagingData<ImageWithUserModel>>(PagingData.empty())

    val collectionImageList: StateFlow<PagingData<ImageWithUserModel>>
        get() = collectionImagesStateFlow

    val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = getNetworkStateUseCase()

    fun searchImages(collectionId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchImagesUseCase(SearchCondition.CollectionImages(collectionId))
                .cachedIn(viewModelScope).collectLatest {
                    collectionImagesStateFlow.value = it
                }
        }
    }
}