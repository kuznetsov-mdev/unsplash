package com.skillbox.unsplash.feature.imagelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.unsplash.data.images.ImagesRepository
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImagesRepository
) : ViewModel() {
    private val imagesStateFlow: Flow<PagingData<ImageItem>> = repository.getPagedImages().cachedIn(viewModelScope)

    val imageList: Flow<PagingData<ImageItem>>
        get() = imagesStateFlow

    fun setLike(imageId: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.setLike(imageId)
                onComplete()
            } catch (t: Throwable) {
                Timber.d("Something wrong")
            }
        }
    }

    fun removeLike(imageId: String, onComplete: () -> Unit) {
        viewModelScope.launch {
            try {
                repository.removeLike(imageId)
                onComplete()
            } catch (t: Throwable) {
                Timber.d("Something wrong")
            }
        }
    }
}