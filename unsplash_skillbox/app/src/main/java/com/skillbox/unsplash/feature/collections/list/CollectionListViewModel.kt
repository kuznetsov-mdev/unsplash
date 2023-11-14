package com.skillbox.unsplash.feature.collections.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.unsplash.data.collections.CollectionsRepository
import com.skillbox.unsplash.feature.collections.model.CollectionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionListViewModel @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) : ViewModel() {
    private val collectionsStateFlow = MutableStateFlow<PagingData<CollectionUiModel>>(PagingData.empty())

    val collectionList: StateFlow<PagingData<CollectionUiModel>>
        get() = collectionsStateFlow


    fun getCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            collectionsRepository.getAll().cachedIn(viewModelScope).collectLatest {
                collectionsStateFlow.value = it
            }
        }
    }
}