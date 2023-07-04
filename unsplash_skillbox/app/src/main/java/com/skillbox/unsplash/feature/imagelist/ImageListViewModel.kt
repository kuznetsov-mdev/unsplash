package com.skillbox.unsplash.feature.imagelist

import androidx.lifecycle.ViewModel
import com.skillbox.unsplash.data.images.ImageListRepositoryApi
import com.skillbox.unsplash.feature.imagelist.data.ImageItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    repository: ImageListRepositoryApi
) : ViewModel() {

    fun getImages(): List<ImageItem> {
        return listOf(
            ImageItem("1", 2, false, "Jack K", "@jack"),
            ImageItem("2", 2, false, "Piter G", "@piter"),
            ImageItem("3", 2, false, "Sam Y", "@sam"),
            ImageItem("4", 2, false, "Ricky I", "@ricky")
        )
    }

}