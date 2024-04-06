package com.skillbox.unsplash.presentation.images.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.unsplash.data.common.SearchCondition
import com.skillbox.unsplash.data.impl.ImageRepository
import com.skillbox.unsplash.data.remote.network.ConnectivityObserver
import com.skillbox.unsplash.data.remote.network.ConnectivityStatus
import com.skillbox.unsplash.domain.model.local.ImageWithUserModel
import com.skillbox.unsplash.domain.model.local.UnsplashSearchQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImageRepository,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {

    private val imagesStateFlow = MutableStateFlow<PagingData<ImageWithUserModel>>(PagingData.empty())

    val imageList: StateFlow<PagingData<ImageWithUserModel>>
        get() = imagesStateFlow

    val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = connectivityObserver.observe()

    fun setLike(imageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.setLike(imageId)
            } catch (t: Throwable) {
                Timber.d("Something wrong when try set like")
            }
        }
    }

    fun removeLike(imageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.removeLike(imageId)
            } catch (t: Throwable) {
                Timber.d("Something wrong when try remove like")
            }
        }
    }

    fun searchImages(searchQuery: UnsplashSearchQuery) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.search(getSearchCondition(searchQuery))
                .cachedIn(viewModelScope)
                .collectLatest {
                    imagesStateFlow.value = it
                }
        }
    }

    private fun getSearchCondition(searchQuery: UnsplashSearchQuery): SearchCondition {
        with(searchQuery) {
            val isImagesSearched = userName == null
            val isUserImagesSearched = userName != null && !likedByUser
            val isLikedByUserImagesSearched = userName != null && likedByUser

            return if (isImagesSearched) {
                return if (query != null) {
                    SearchCondition.SearchString(query)
                } else {
                    SearchCondition.Empty
                }
            } else if (isUserImagesSearched) {
                SearchCondition.UserImages(userName!!)
            } else if (isLikedByUserImagesSearched) {
                SearchCondition.LikedUserImages(userName!!)
            } else {
                SearchCondition.Empty
            }
        }
    }
}