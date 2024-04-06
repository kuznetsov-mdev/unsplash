package com.skillbox.unsplash.presentation.collections.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.unsplash.common.network.ConnectivityStatus
import com.skillbox.unsplash.common.network.api.ConnectivityObserver
import com.skillbox.unsplash.data.collections.CollectionsRepository
import com.skillbox.unsplash.domain.model.local.CollectionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CollectionListViewModel @Inject constructor(
    private val collectionsRepository: CollectionsRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {
    private val collectionsSharedFlow = MutableSharedFlow<PagingData<CollectionUiModel>>()

    val collectionList: SharedFlow<PagingData<CollectionUiModel>>
        get() = collectionsSharedFlow.asSharedFlow()

    val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = connectivityObserver.observe()

    fun getCollections(userName: String?) {
        Timber.tag("LifecycleLog ViewModel").d("${this.javaClass.simpleName} -> getCollections for user = $userName")
        viewModelScope.launch(Dispatchers.IO) {
            collectionsRepository.getCollections(userName)
                .cachedIn(viewModelScope).collectLatest {
                    collectionsSharedFlow.emit(it)
                }
        }
    }
}