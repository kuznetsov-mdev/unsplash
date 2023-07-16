package com.skillbox.unsplash.feature.imagelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.skillbox.unsplash.data.images.ImageListRepositoryApi
import com.skillbox.unsplash.data.images.model.RemoteImage
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImageListRepositoryApi
) : ViewModel() {
    private val imagesLiveData = MutableLiveData<List<ImageItem>>(getImages())
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

    fun getImages(): List<ImageItem> {
        return listOf(
            ImageItem(
                "1",
                2,
                false,
                "Jack K",
                "@jack",
                "",
                "https://unsplash.com/photos/Ih4fgtqy0Y8/download?ixid=M3wxMjA3fDB8MXxhbGx8OXx8fHx8fDJ8fDE2ODg5MTM0MTN8&force=true"
            ),
            ImageItem(
                "2",
                5,
                true,
                "Piter G",
                "@piter",
                "",
                "https://unsplash.com/photos/VowIFDxogG4/download?ixid=M3wxMjA3fDB8MXxjb2xsZWN0aW9ufDR8OTIzOTA2fHx8fHwyfHwxNjg4OTM4NTcxfA&force=true"
            ),
            ImageItem(
                "3",
                2,
                false,
                "Sam Y",
                "@sam",
                "",
                "https://unsplash.com/photos/ny6qxqv_m04/download?ixid=M3wxMjA3fDB8MXxjb2xsZWN0aW9ufDV8OTIzOTA2fHx8fHwyfHwxNjg4OTM4NTcxfA&force=true"
            ),
            ImageItem(
                "4",
                2,
                false,
                "Ricky I",
                "@ricky",
                "",
                "https://unsplash.com/photos/nwxK2Znr-is/download?ixid=M3wxMjA3fDB8MXxjb2xsZWN0aW9ufDZ8OTIzOTA2fHx8fHwyfHwxNjg4OTM4NTcxfA&force=true"
            ),
            ImageItem(
                "5",
                2,
                false,
                "Ricky I",
                "@ricky",
                "",
                "https://unsplash.com/photos/aX8JLb16lDc/download?ixid=M3wxMjA3fDB8MXxjb2xsZWN0aW9ufDExfDkyMzkwNnx8fHx8Mnx8MTY4ODkzODU3MXw&force=true"
            ),
            ImageItem(
                "6",
                2,
                false,
                "Ricky I",
                "@ricky",
                "",
                "https://unsplash.com/photos/iIrB37J5yfA/download?ixid=M3wxMjA3fDB8MXxjb2xsZWN0aW9ufDl8OTIzOTA2fHx8fHwyfHwxNjg4OTM4NTcxfA&force=true"
            ),
            ImageItem(
                "7",
                2,
                false,
                "Ricky I",
                "@ricky",
                "",
                "https://unsplash.com/photos/yBgIUFwlCLA/download?ixid=M3wxMjA3fDB8MXxjb2xsZWN0aW9ufDd8OTIzOTA2fHx8fHwyfHwxNjg4OTM4NTcxfA&force=true"
            )
        )
    }

}