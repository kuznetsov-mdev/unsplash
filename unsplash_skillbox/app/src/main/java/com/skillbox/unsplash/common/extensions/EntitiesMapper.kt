package com.skillbox.unsplash.common.extensions

import com.skillbox.unsplash.data.collections.retrofit.model.CollectionRetrofitModel
import com.skillbox.unsplash.data.images.retrofit.model.ImageRetrofitModel
import com.skillbox.unsplash.data.images.retrofit.model.detail.ImageDetailRetrofitModel
import com.skillbox.unsplash.data.images.room.model.ImageRoomModel
import com.skillbox.unsplash.data.images.room.model.UserRoomModel
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithUserRoomModel
import com.skillbox.unsplash.feature.collections.model.CollectionUiModel
import com.skillbox.unsplash.feature.images.detail.model.ExifUiModel
import com.skillbox.unsplash.feature.images.detail.model.ImageDetailUiModel
import com.skillbox.unsplash.feature.images.detail.model.LocationUiModel
import com.skillbox.unsplash.feature.images.detail.model.StatisticUiModel
import com.skillbox.unsplash.feature.images.list.model.ImageWithUserUiModel
import com.skillbox.unsplash.feature.images.model.ImageUiModel
import com.skillbox.unsplash.feature.images.model.UserUiModel

fun ImageRetrofitModel.toRoomImageEntity(
    cachedImagePath: String,
    cachedAvatarPath: String,
    searchQuery: String
): ImageWithUserRoomModel {
    val userRoomModel = UserRoomModel(
        this.user.id,
        this.user.name,
        this.user.nickname,
        this.user.profileImage.medium,
        cachedAvatarPath,
        this.user.biography ?: ""
    )
    val imageRoomModel = ImageRoomModel(
        this.id,
        this.user.id,
        this.description ?: "",
        this.likes,
        this.likedByUser,
        this.urls.thumb,
        cachedImagePath,
        searchQuery
    )
    return ImageWithUserRoomModel(imageRoomModel, userRoomModel)
}

fun ImageWithUserRoomModel.toImageItem(): ImageWithUserUiModel {
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

fun ImageDetailRetrofitModel.toDetailImageItem(cachedImagePath: String, cachedAuthorAvatarPath: String): ImageDetailUiModel {
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

fun CollectionRetrofitModel.toUiEntity(): CollectionUiModel {
    return CollectionUiModel(
        this.id,
        this.title,
        this.description ?: "",
        UserUiModel(
            this.user.id,
            this.user.nickname,
            this.user.name,
            this.user.biography ?: "",
            this.user.profileImage.small,
            "stub"
        )
    )
}