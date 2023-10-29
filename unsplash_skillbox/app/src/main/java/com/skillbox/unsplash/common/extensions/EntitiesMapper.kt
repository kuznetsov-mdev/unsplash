package com.skillbox.unsplash.common.extensions

import com.skillbox.unsplash.data.model.retrofit.collection.RetrofitCollectionModel
import com.skillbox.unsplash.data.model.retrofit.image.RetrofitImageModel
import com.skillbox.unsplash.data.model.retrofit.image.detail.RetrofitImageDetailModel
import com.skillbox.unsplash.data.model.room.RoomImageModel
import com.skillbox.unsplash.data.model.room.RoomUserModel
import com.skillbox.unsplash.data.model.room.relations.RoomImageWithUserModel
import com.skillbox.unsplash.feature.collections.model.UiCollectionModel
import com.skillbox.unsplash.feature.images.detail.model.UiExifModel
import com.skillbox.unsplash.feature.images.detail.model.UiImageDetailModel
import com.skillbox.unsplash.feature.images.detail.model.UiLocationModel
import com.skillbox.unsplash.feature.images.detail.model.UiStatisticModel
import com.skillbox.unsplash.feature.images.list.model.UiImageWithUserModel
import com.skillbox.unsplash.feature.images.model.UiImageModel
import com.skillbox.unsplash.feature.images.model.UiUserModel

fun RetrofitImageModel.toRoomImageEntity(
    cachedImagePath: String,
    cachedAvatarPath: String,
    searchQuery: String
): RoomImageWithUserModel {
    val roomUserModel = RoomUserModel(
        this.user.id,
        this.user.name,
        this.user.nickname,
        this.user.profileImage.medium,
        cachedAvatarPath,
        this.user.biography ?: ""
    )
    val roomImageModel = RoomImageModel(
        this.id,
        this.user.id,
        this.description ?: "",
        this.likes,
        this.likedByUser,
        this.urls.thumb,
        cachedImagePath,
        searchQuery
    )
    return RoomImageWithUserModel(roomImageModel, roomUserModel)
}

fun RoomImageWithUserModel.toImageItem(): UiImageWithUserModel {
    return UiImageWithUserModel(
        UiImageModel(
            this.image.id,
            this.image.description,
            this.image.likes,
            this.image.likedByUser,
            this.image.preview,
            this.image.cachedPreview
        ),
        UiUserModel(
            this.author.id,
            this.author.nickName,
            this.author.name,
            this.author.biography,
            this.author.profileImage,
            this.author.cachedProfileImage
        )
    )
}

fun RetrofitImageDetailModel.toDetailImageItem(cachedImagePath: String, cachedAuthorAvatarPath: String): UiImageDetailModel {
    return UiImageDetailModel(
        UiImageModel(this.id, this.description ?: "", this.likes, this.likedByUser, this.urls.small, cachedImagePath),
        this.width,
        this.height,
        UiUserModel(
            this.user.id,
            this.user.nickname,
            this.user.name,
            this.user.biography ?: "",
            this.user.profileImage.small,
            cachedAuthorAvatarPath
        ),
        UiExifModel(
            this.exif.make ?: "Unknown",
            this.exif.model ?: "Unknown",
            this.exif.name ?: "Unknown",
            this.exif.focalLength ?: "Unknown",
            this.exif.aperture ?: "Unknown",
            this.exif.exposureTime ?: "Unknown",
            this.exif.iso ?: 0
        ),
        this.tags.map { it.title },
        UiLocationModel(
            this.location.city,
            this.location.country,
            this.location.name,
            this.location.position.latitude,
            this.location.position.longitude
        ),
        UiStatisticModel(
            this.downloads,
            0,
            this.likes
        ),
        this.links.download
    )
}

fun RetrofitCollectionModel.toUiEntity(): UiCollectionModel {
    return UiCollectionModel(
        this.id,
        this.title,
        this.description
    )
}