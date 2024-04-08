package com.skillbox.unsplash.presentation.images.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.skillbox.unsplash.common.SearchCondition
import com.skillbox.unsplash.data.remote.network.ConnectivityStatus
import com.skillbox.unsplash.domain.model.ImageWithUserModel
import com.skillbox.unsplash.domain.model.UnsplashSearchQuery
import com.skillbox.unsplash.domain.usecase.common.GetNetworkStateUseCase
import com.skillbox.unsplash.domain.usecase.image.GetImagesUseCase
import com.skillbox.unsplash.domain.usecase.image.LikeImageUseCase
import com.skillbox.unsplash.domain.usecase.image.UnlikeImageUseCase
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
    private val getImagesUseCase: GetImagesUseCase,
    private val likeImageUseCase: LikeImageUseCase,
    private val unlikeImageUseCase: UnlikeImageUseCase,
    private val getNetworkStateUseCase: GetNetworkStateUseCase
) : ViewModel() {

    private val imagesStateFlow = MutableStateFlow<PagingData<ImageWithUserModel>>(PagingData.empty())
    val imageList: StateFlow<PagingData<ImageWithUserModel>>
        get() = imagesStateFlow

    val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = getNetworkStateUseCase()

    fun getImages(searchQuery: UnsplashSearchQuery) {
        viewModelScope.launch(Dispatchers.IO) {
            getImagesUseCase(getSearchCondition(searchQuery)).cachedIn(viewModelScope)
                .collectLatest {
                    imagesStateFlow.value = it
                }
        }
    }

    fun setLike(imageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                likeImageUseCase(imageId)
            } catch (t: Throwable) {
                Timber.d("Something wrong when try set like")
            }
        }
    }

    fun removeLike(imageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                unlikeImageUseCase(imageId)
            } catch (t: Throwable) {
                Timber.d("Something wrong when try remove like")
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
                    SearchCondition.SearchQueryImages(query)
                } else {
                    SearchCondition.AllImages
                }
            } else if (isUserImagesSearched) {
                SearchCondition.UserImages(userName!!)
            } else if (isLikedByUserImagesSearched) {
                SearchCondition.LikedByUserImages(userName!!)
            } else {
                SearchCondition.AllImages
            }
        }
    }
}