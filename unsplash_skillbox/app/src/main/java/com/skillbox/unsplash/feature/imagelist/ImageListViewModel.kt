package com.skillbox.unsplash.feature.imagelist

import androidx.lifecycle.ViewModel
import com.skillbox.unsplash.data.images.ImageListRepositoryApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    repository: ImageListRepositoryApi
) : ViewModel() {


}