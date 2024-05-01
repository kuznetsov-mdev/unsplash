package com.skillbox.unsplash.presentation.images.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.skillbox.unsplash.common.LoadState
import com.skillbox.unsplash.data.remote.network.ConnectivityStatus
import com.skillbox.unsplash.domain.model.detail.ImageDetailModel
import com.skillbox.unsplash.domain.usecase.common.GetNetworkStateUseCase
import com.skillbox.unsplash.domain.usecase.image.GetImageDetailsUseCase
import com.skillbox.unsplash.domain.usecase.image.LikeImageUseCase
import com.skillbox.unsplash.domain.usecase.image.SaveImageToGalleryUseCase
import com.skillbox.unsplash.domain.usecase.image.UnlikeImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val getNetworkStateUseCase: GetNetworkStateUseCase,
    private val getImageDetailsUseCase: GetImageDetailsUseCase,
    private val likeImageUseCase: LikeImageUseCase,
    private val unlikeImageUseCase: UnlikeImageUseCase,
    private val saveImageToGalleryUseCase: SaveImageToGalleryUseCase
) : ViewModel() {
    private val imageDetailMutableFlow: MutableStateFlow<ImageDetailModel?> = MutableStateFlow(null)
    private val isDataLoadingMutableFlow: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val permissionGrantedMutableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)


    private val imageDetailMutableStateFlow: MutableStateFlow<LoadState<ImageDetailModel>> =
        MutableStateFlow(LoadState.Loading)

    val imageDetailStateFlow: StateFlow<LoadState<ImageDetailModel>>
        get() = imageDetailMutableStateFlow.asStateFlow()


    val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = getNetworkStateUseCase()

    val imageDetailFlow: StateFlow<ImageDetailModel?>
        get() = imageDetailMutableFlow

    val isDataLoadingFlow: StateFlow<Boolean>
        get() = isDataLoadingMutableFlow

    val permissionGrantedStateFlow: StateFlow<Boolean>
        get() = permissionGrantedMutableStateFlow

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

    fun getImageDetailInfo(imageId: String) {
        imageDetailMutableStateFlow.value = LoadState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            imageDetailMutableStateFlow.value = getImageDetailsUseCase(imageId)
//            imageDetailMutableFlow.value = repository.getImageDetailInfo(imageId)
//            isDataLoadingMutableFlow.value = false

        }
    }

    fun startImageSavingToGalleryWork(id: String, downloadImageUrl: String): LiveData<WorkInfo> {
        return saveImageToGalleryUseCase(id, downloadImageUrl)
    }

    fun permissionGranted() {
        permissionGrantedMutableStateFlow.value = true
    }

    fun permissionDenied() {
        permissionGrantedMutableStateFlow.value = false
    }
}