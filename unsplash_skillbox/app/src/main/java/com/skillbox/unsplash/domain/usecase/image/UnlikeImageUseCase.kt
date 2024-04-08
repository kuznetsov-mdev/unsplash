package com.skillbox.unsplash.domain.usecase.image

import com.skillbox.unsplash.domain.api.repository.ImageRepositoryApi
import javax.inject.Inject

class UnlikeImageUseCase @Inject constructor(
    private val repository: ImageRepositoryApi
) {
    suspend operator fun invoke(imageId: String) {
        repository.unlikeImage(imageId)
    }

}