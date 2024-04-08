package com.skillbox.unsplash.domain.usecase.image

import com.skillbox.unsplash.domain.api.repository.ImageRepositoryApi
import javax.inject.Inject

class ShareImageUseCase @Inject constructor(
    private val repository: ImageRepositoryApi
) {

}