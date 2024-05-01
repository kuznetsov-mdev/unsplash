package com.skillbox.unsplash.domain.usecase.image

import com.skillbox.unsplash.common.LoadState
import com.skillbox.unsplash.common.extensions.toDetailImageItem
import com.skillbox.unsplash.domain.api.repository.ImageRepositoryApi
import com.skillbox.unsplash.domain.model.detail.ImageDetailModel
import javax.inject.Inject

class GetImageDetailsUseCase @Inject constructor(
    private val repository: ImageRepositoryApi
) {
    suspend operator fun invoke(imageId: String): LoadState<ImageDetailModel> {
        return when (val result = repository.getImageDetailInfo(imageId)) {
            is LoadState.Success -> LoadState.Success(result.result.toDetailImageItem())
            is LoadState.Error -> LoadState.Error(result.reason)
            is LoadState.Loading -> result
        }
    }
}