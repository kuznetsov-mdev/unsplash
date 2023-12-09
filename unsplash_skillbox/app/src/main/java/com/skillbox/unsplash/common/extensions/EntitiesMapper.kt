package com.skillbox.unsplash.common.extensions

import com.skillbox.unsplash.data.collections.retrofit.model.CollectionDto
import com.skillbox.unsplash.data.collections.room.model.CollectionEntity
import com.skillbox.unsplash.data.collections.room.model.relations.CollectionWithImagesEntity
import com.skillbox.unsplash.data.collections.room.model.relations.CollectionWithUserAndImagesEntity
import com.skillbox.unsplash.data.images.retrofit.model.ImageDto
import com.skillbox.unsplash.data.images.retrofit.model.detail.ImageDetailDto
import com.skillbox.unsplash.data.images.room.model.ImageEntity
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithUserEntity
import com.skillbox.unsplash.data.profile.retrofit.model.UserProfileDto
import com.skillbox.unsplash.data.user.room.model.UserEntity
import com.skillbox.unsplash.feature.collections.model.CollectionUiModel
import com.skillbox.unsplash.feature.images.detail.model.ExifUiModel
import com.skillbox.unsplash.feature.images.detail.model.ImageDetailUiModel
import com.skillbox.unsplash.feature.images.detail.model.LocationUiModel
import com.skillbox.unsplash.feature.images.detail.model.StatisticUiModel
import com.skillbox.unsplash.feature.images.list.model.ImageWithUserUiModel
import com.skillbox.unsplash.feature.images.model.ImageUiModel
import com.skillbox.unsplash.feature.images.model.UserUiModel
import com.skillbox.unsplash.feature.profile.model.ProfileUiModel

fun ImageDto.toRoomImageEntity(
    cachedImagePath: String,
    cachedAvatarPath: String,
    searchQuery: String
): ImageWithUserEntity {
    val userEntity = UserEntity(
        this.user.id,
        this.user.name,
        this.user.nickname,
        this.user.profileImage.medium,
        cachedAvatarPath,
        this.user.biography ?: ""
    )
    val imageEntity = ImageEntity(
        this.id,
        this.user.id,
        this.description ?: "",
        this.likes,
        this.likedByUser,
        this.urls.thumb,
        cachedImagePath,
        searchQuery
    )
    return ImageWithUserEntity(imageEntity, userEntity)
}

fun ImageWithUserEntity.toImageUiModel(): ImageWithUserUiModel {
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

fun ImageDetailDto.toDetailImageItem(cachedImagePath: String, cachedAuthorAvatarPath: String): ImageDetailUiModel {
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
): CollectionWithUserAndImagesEntity {
    return CollectionWithUserAndImagesEntity(
        CollectionWithImagesEntity(
            CollectionEntity(
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
        UserEntity(
            this.user.id,
            this.user.name,
            this.user.nickname,
            this.user.profileImage.medium,
            userAvatarLocation,
            this.user.biography ?: ""
        )
    )
}

fun CollectionWithUserAndImagesEntity.toCollectionUiModel(): CollectionUiModel {
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

fun UserProfileDto.toUiModel(): ProfileUiModel {
    return ProfileUiModel(
        this.id,
        this.fullName,
        this.nickname,
        this.email,
        this.location,
        this.totalPhotos,
        this.totalLikes,
        this.totalCollections,
        this.biography

    )
}