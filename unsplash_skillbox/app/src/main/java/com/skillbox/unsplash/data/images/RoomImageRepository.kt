package com.skillbox.unsplash.data.images

import com.skillbox.unsplash.data.images.model.RemoteImage

class RoomImageRepository : ImagesRepository {
    override suspend fun setLike(imageId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun removeLike(imageId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getImageList(pageNumber: Int, pageSize: Int): List<RemoteImage> {
        TODO("Not yet implemented")
    }
}