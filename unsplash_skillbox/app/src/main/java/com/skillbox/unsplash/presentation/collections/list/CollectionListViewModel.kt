package com.skillbox.unsplash.presentation.collections.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.unsplash.data.remote.network.ConnectivityStatus
import com.skillbox.unsplash.data.repository.CollectionsRepositoryImpl
import com.skillbox.unsplash.domain.model.CollectionModel
import com.skillbox.unsplash.domain.usecase.collection.GetCollectionsUseCase
import com.skillbox.unsplash.domain.usecase.common.GetNetworkStateUseCase
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
    private val collectionsRepository: CollectionsRepositoryImpl,
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getCollectionsUseCase: GetCollectionsUseCase
) : ViewModel() {
    private val collectionsSharedFlow = MutableSharedFlow<PagingData<CollectionModel>>()

    val collectionList: SharedFlow<PagingData<CollectionModel>>
        get() = collectionsSharedFlow.asSharedFlow()

    val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = getNetworkStateUseCase()

    fun getCollections(userName: String?) {
        Timber.tag("LifecycleLog ViewModel").d("${this.javaClass.simpleName} -> getCollections for user = $userName")
        viewModelScope.launch(Dispatchers.IO) {
            getCollectionsUseCase(userName).cachedIn(viewModelScope).collectLatest {
                collectionsSharedFlow.emit(it)
            }
        }
    }
}