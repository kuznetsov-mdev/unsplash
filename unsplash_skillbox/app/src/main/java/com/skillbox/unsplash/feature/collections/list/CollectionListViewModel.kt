package com.skillbox.unsplash.feature.collections.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.skillbox.unsplash.data.collections.CollectionsRepository
import com.skillbox.unsplash.feature.collections.model.CollectionUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionListViewModel @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) : ViewModel() {

    private val collectionsStateFlow = MutableStateFlow<PagingData<CollectionUiModel>>(PagingData.empty())

    fun getCollections() {
        viewModelScope.launch(Dispatchers.IO) {
            collectionsRepository.getAll(1, 10)
        }
    }
}