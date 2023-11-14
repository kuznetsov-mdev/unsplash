package com.skillbox.unsplash.common.extensions

import com.skillbox.unsplash.data.collections.retrofit.model.CollectionDto
import com.skillbox.unsplash.data.collections.room.model.CollectionRoomModel
import com.skillbox.unsplash.data.collections.room.model.relations.CollectionWithImagesRoomModel
import com.skillbox.unsplash.data.collections.room.model.relations.CollectionWithUserAndImagesRoomModel
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

fun ImageWithUserRoomModel.toImageUiModel(): ImageWithUserUiModel {
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
            this.user.id,
            this.user.nickName,
            this.user.name,
            this.user.biography,
            this.user.profileImage,
            this.user.cachedProfileImage
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

fun CollectionDto.toRoomEntity(
    previewLocation: String,
    userAvatarLocation: String
): CollectionWithUserAndImagesRoomModel {
    return CollectionWithUserAndImagesRoomModel(
        CollectionWithImagesRoomModel(
            CollectionRoomModel(
                this.id,
                this.user.id,
                this.title,
                this.description ?: "",
                this.publishedAt,
                this.updatedAt,
                this.totalPhotos,
                previewLocation
            )
        ),
        UserRoomModel(
            this.user.id,
            this.user.name,
            this.user.nickname,
            this.user.profileImage.medium,
            userAvatarLocation,
            this.user.biography ?: ""
        )
    )
}

fun CollectionWithUserAndImagesRoomModel.toCollectionUiModel(): CollectionUiModel {
    return CollectionUiModel(
        this.collectionWithImages.collection.id,
        this.collectionWithImages.collection.title,
        this.collectionWithImages.collection.description,
        this.collectionWithImages.collection.totalPhotos,
        this.collectionWithImages.collection.cachedCoverPhoto,
        UserUiModel(
            this.user.id,
            this.user.nickName,
            this.user.name,
            this.user.biography,
            this.user.profileImage,
            this.user.cachedProfileImage
        )
    )
}