package com.skillbox.unsplash.feature.collections.list

import androidx.lifecycle.ViewModel
import com.skillbox.unsplash.data.collections.CollectionsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CollectionListViewModel @Inject constructor(
    private val collectionsRepository: CollectionsRepository
) : ViewModel() {


}