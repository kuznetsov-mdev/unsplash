package com.skillbox.unsplash.feature.imagelist

import androidx.lifecycle.ViewModel
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    // private val repository: ImageListRepositoryApi
) : ViewModel() {

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
                2,
                false,
                "Piter G",
                "@piter",
                "",
                "https://unsplash.com/photos/Ih4fgtqy0Y8/download?ixid=M3wxMjA3fDB8MXxhbGx8OXx8fHx8fDJ8fDE2ODg5MTM0MTN8&force=true"
            ),
            ImageItem(
                "3",
                2,
                false,
                "Sam Y",
                "@sam",
                "",
                "https://unsplash.com/photos/Ih4fgtqy0Y8/download?ixid=M3wxMjA3fDB8MXxhbGx8OXx8fHx8fDJ8fDE2ODg5MTM0MTN8&force=true"
            ),
            ImageItem(
                "4",
                2,
                false,
                "Ricky I",
                "@ricky",
                "",
                "https://unsplash.com/photos/Ih4fgtqy0Y8/download?ixid=M3wxMjA3fDB8MXxhbGx8OXx8fHx8fDJ8fDE2ODg5MTM0MTN8&force=true"
            )
        )
    }

}