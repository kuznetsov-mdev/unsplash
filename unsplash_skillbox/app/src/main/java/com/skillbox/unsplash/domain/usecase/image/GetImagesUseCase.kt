package com.skillbox.unsplash.domain.usecase.image

import androidx.paging.PagingData
import androidx.paging.map
import com.skillbox.unsplash.common.SearchCondition
import com.skillbox.unsplash.common.extensions.toImageUiModel
import com.skillbox.unsplash.domain.api.repository.ImageRepositoryApi
import com.skillbox.unsplash.domain.model.ImageWithUserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepositoryApi
) {
    operator fun invoke(condition: SearchCondition): Flow<PagingData<ImageWithUserModel>> =
        imageRepository.search(condition)
            .map { pagingData ->
                pagingData.map { imageEntity ->
                    imageEntity.toImageUiModel()
                }
            }
}