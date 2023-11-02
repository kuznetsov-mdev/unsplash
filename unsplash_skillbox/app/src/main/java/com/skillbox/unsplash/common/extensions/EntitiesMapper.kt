package com.skillbox.unsplash.common.extensions

import com.skillbox.unsplash.data.model.retrofit.collection.RetrofitCollectionModel
import com.skillbox.unsplash.data.model.retrofit.image.RetrofitImageModel
import com.skillbox.unsplash.data.model.retrofit.image.detail.RetrofitImageDetailModel
import com.skillbox.unsplash.data.model.room.RoomImageModel
import com.skillbox.unsplash.data.model.room.RoomUserModel
import com.skillbox.unsplash.data.model.room.relations.RoomImageWithUserModel
import com.skillbox.unsplash.feature.collections.model.CollectionUiModel
import com.skillbox.unsplash.feature.images.detail.model.ExifUiModel
import com.skillbox.unsplash.feature.images.detail.model.ImageDetailUiModel
import com.skillbox.unsplash.feature.images.detail.model.LocationUiModel
import com.skillbox.unsplash.feature.images.detail.model.StatisticUiModel
import com.skillbox.unsplash.feature.images.list.model.ImageWithUserUiModel
import com.skillbox.unsplash.feature.images.model.ImageUiModel
import com.skillbox.unsplash.feature.images.model.UserUiModel

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

fun RoomImageWithUserModel.toImageItem(): ImageWithUserUiModel {
    return ImageWithUserUiModel(
        ImageUiModel(
            this.image.id,
            this.image.description,
            this.image.likes,
            this.image.likedByUser,
            this.image.preview,
            this.image.cachedPreview
        ),
        UserUiModel(
            this.author.id,
            this.author.nickName,
            this.author.name,
            this.author.biography,
            this.author.profileImage,
            this.author.cachedProfileImage
        )
    )
}

fun RetrofitImageDetailModel.toDetailImageItem(cachedImagePath: String, cachedAuthorAvatarPath: String): ImageDetailUiModel {
    return ImageDetailUiModel(
        ImageUiModel(this.id, this.description ?: "", this.likes, this.likedByUser, this.urls.small, cachedImagePath),
        this.width,
        this.height,
        UserUiModel(
            this.user.id,
            this.user.nickname,
            this.user.name,
            this.user.biography ?: "",
            this.user.profileImage.small,
            cachedAuthorAvatarPath
        ),
        ExifUiModel(
            this.exif.make ?: "Unknown",
            this.exif.model ?: "Unknown",
            this.exif.name ?: "Unknown",
            this.exif.focalLength ?: "Unknown",
            this.exif.aperture ?: "Unknown",
            this.exif.exposureTime ?: "Unknown",
            this.exif.iso ?: 0
        ),
        this.tags.map { it.title },
        LocationUiModel(
            this.location.city,
            this.location.country,
            this.location.name,
            this.location.position.latitude,
            this.location.position.longitude
        ),
        StatisticUiModel(
            this.downloads,
            0,
            this.likes
        ),
        this.links.download
    )
}

fun RetrofitCollectionModel.toUiEntity(): CollectionUiModel {
    return CollectionUiModel(
        this.id,
        this.title,
        this.description
    )
}