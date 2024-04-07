package com.skillbox.unsplash.presentation.images.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkInfo
import com.skillbox.unsplash.data.remote.network.ConnectivityObserver
import com.skillbox.unsplash.data.remote.network.ConnectivityStatus
import com.skillbox.unsplash.domain.api.repository.ImageRepositoryApi
import com.skillbox.unsplash.domain.model.detail.ImageDetailModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val repository: ImageRepositoryApi,
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {
    private val imageDetailMutableFlow: MutableStateFlow<ImageDetailModel?> = MutableStateFlow(null)
    private val isDataLoadingMutableFlow: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val permissionGrantedMutableStateFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val connectivityStateFlow: Flow<ConnectivityStatus>
        get() = connectivityObserver.observe()

    val imageDetailFlow: StateFlow<ImageDetailModel?>
        get() = imageDetailMutableFlow

    val isDataLoadingFlow: StateFlow<Boolean>
        get() = isDataLoadingMutableFlow

    val permissionGrantedStateFlow: StateFlow<Boolean>
        get() = permissionGrantedMutableStateFlow

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

    fun getImageDetailInfo(imageId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            imageDetailMutableFlow.value = repository.getImageDetailInfo(imageId)
            isDataLoadingMutableFlow.value = false
        }
    }

    fun startImageSavingToGalleryWork(id: String, downloadImageUrl: String): LiveData<WorkInfo> {
        return repository.startImageSavingToGalleryWork(id, downloadImageUrl)
    }

    fun permissionGranted() {
        permissionGrantedMutableStateFlow.value = true
    }

    fun permissionDenied() {
        permissionGrantedMutableStateFlow.value = false
    }
}