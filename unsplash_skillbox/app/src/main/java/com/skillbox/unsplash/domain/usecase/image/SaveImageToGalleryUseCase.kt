package com.skillbox.unsplash.domain.usecase.image

import androidx.lifecycle.LiveData
import androidx.work.WorkInfo
import com.skillbox.unsplash.domain.api.repository.ImageRepositoryApi
import javax.inject.Inject

class SaveImageToGalleryUseCase @Inject constructor(
    private val imageRepositoryApi: ImageRepositoryApi
) {
    operator fun invoke(id: String, downloadImageUrl: String): LiveData<WorkInfo> {
        return imageRepositoryApi.startImageSavingToGalleryWork(id, downloadImageUrl)
    }
}