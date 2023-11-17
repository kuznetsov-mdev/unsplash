package com.skillbox.unsplash.feature.collections.detail

import androidx.lifecycle.ViewModel
import com.skillbox.unsplash.data.collections.CollectionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectionDetailViewModel @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) : ViewModel() {
    var isNetworkAvailableState = true
}