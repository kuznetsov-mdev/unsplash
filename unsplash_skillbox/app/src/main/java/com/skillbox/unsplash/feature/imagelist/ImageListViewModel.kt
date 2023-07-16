package com.skillbox.unsplash.feature.imagelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.unsplash.data.images.ImageListRepositoryApi
import com.skillbox.unsplash.data.images.model.RemoteImage
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImageListRepositoryApi
) : ViewModel() {
    private val imagesLiveData = MutableLiveData<List<ImageItem>>()
    private val isLoadingLiveData = MutableLiveData<Boolean>()

    val images: LiveData<List<ImageItem>>
        get() = imagesLiveData

    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    fun getImageList() {
        isLoadingLiveData.postValue(true)
        return repository.getImageList({ images ->
            imagesLiveData.postValue(getImageItemList(images))
            isLoadingLiveData.postValue(false)
        },
            {
                imagesLiveData.postValue(emptyList())
                isLoadingLiveData.postValue(false)
            })
    }

    fun setLike(imageId: String, onComplete: () -> Unit) {
        repository.setLike(
            imageId = imageId,
            onComplete,
            { Timber.d("Something wrong") }
        )
    }

    fun removeLike(imageId: String, onComplete: () -> Unit) {
        repository.removeLike(
            imageId = imageId,
            onComplete,
            { Timber.d("Something wrong") }
        )
    }

    private fun getImageItemList(remoteImageList: List<RemoteImage>): List<ImageItem> {
        val result = mutableListOf<ImageItem>()
        remoteImageList.forEach { remoteImage ->
            val imageItem = ImageItem(
                remoteImage.id,
                remoteImage.likes,
                remoteImage.likedByUser,
                remoteImage.user.name,
                remoteImage.user.nickname,
                remoteImage.user.profileImage.small,
                remoteImage.urls.small
            )
            result.add(imageItem)
        }
        return result;
    }


}