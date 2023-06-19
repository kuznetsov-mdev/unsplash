package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.data.images.model.RemoteImage

interface ImagesRepositoryApi {

    fun getAll(
        onComplete: (List<RemoteImage>) -> Unit,
        onError: (Throwable) -> Unit
    )
}