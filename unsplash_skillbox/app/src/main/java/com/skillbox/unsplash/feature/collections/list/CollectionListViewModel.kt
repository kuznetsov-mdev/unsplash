package com.skillbox.unsplash.feature.collections.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.unsplash.common.network.ConnectivityStatus
import com.skillbox.unsplash.common.network.api.ConnectivityObserver
import com.skillbox.unsplash.data.collections.CollectionsRepository
import com.skillbox.unsplash.feature.collections.model.CollectionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionListViewModel @Inject constructor(
    private val collectionsRepository: CollectionsRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {
    private val collectionsStateFlow = MutableStateFlow<PagingData<CollectionUiModel>>(PagingData.empty())

    val collectionList: StateFlow<PagingData<CollectionUiModel>>
        get() = collectionsStateFlow

    val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = connectivityObserver.observe()

    fun getCollections(userName: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            collectionsRepository.getCollections(userName)
                .cachedIn(viewModelScope).collectLatest {
                    collectionsStateFlow.value = it
                }
        }
    }
}