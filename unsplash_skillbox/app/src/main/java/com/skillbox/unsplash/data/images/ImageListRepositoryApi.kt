package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.data.images.model.RemoteImage

interface ImageListRepositoryApi {

    fun getImageList(
        onComplete: (List<RemoteImage>) -> Unit,
        onError: (Throwable) -> Unit
    )

    fun setLike(
        imageId: String,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    )

    fun removeLike(
        imageId: String,
        onComplete: () -> Unit,
        onError: (Throwable) -> Unit
    )
}