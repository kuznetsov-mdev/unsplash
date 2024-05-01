package com.skillbox.unsplash.common.extensions

import com.skillbox.unsplash.data.local.db.entities.CollectionEntity
import com.skillbox.unsplash.data.local.db.entities.ImageEntity
import com.skillbox.unsplash.data.local.db.entities.UserEntity
import com.skillbox.unsplash.data.local.db.entities.image.ImageWithUserEntity
import com.skillbox.unsplash.data.local.db.entities.relations.CollectionWithImagesEntity
import com.skillbox.unsplash.data.local.db.entities.relations.CollectionWithUserAndImagesEntity
import com.skillbox.unsplash.data.remote.dto.CollectionDto
import com.skillbox.unsplash.data.remote.dto.ImageDto
import com.skillbox.unsplash.data.remote.dto.image.ImageDetailDto
import com.skillbox.unsplash.data.remote.dto.user.UserProfileDto
import com.skillbox.unsplash.domain.model.CollectionModel
import com.skillbox.unsplash.domain.model.ImageModel
import com.skillbox.unsplash.domain.model.ImageWithUserModel
import com.skillbox.unsplash.domain.model.ProfileModel
import com.skillbox.unsplash.domain.model.UserModel
import com.skillbox.unsplash.domain.model.detail.ExifModel
import com.skillbox.unsplash.domain.model.detail.ImageDetailModel
import com.skillbox.unsplash.domain.model.detail.LocationModel
import com.skillbox.unsplash.domain.model.detail.StatisticModel

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

fun ImageWithUserEntity.toImageUiModel(): ImageWithUserModel {
    return ImageWithUserModel(
        ImageModel(
            this.image.id,
            this.image.description,
            this.image.likes,
            this.image.likedByUser,
            this.image.preview,
            this.image.cachedPreview
        ),
        UserModel(
            this.user.id,
            this.user.nickName,
            this.user.name,
            this.user.biography,
            this.user.profileImage,
            this.user.cachedProfileImage
        )
    )
}

fun ImageDetailDto.toDetailImageItem(cachedImagePath: String = "stub", cachedAuthorAvatarPath: String = "stub"): ImageDetailModel {
    return ImageDetailModel(
        ImageModel(this.id, this.description ?: "", this.likes, this.likedByUser, this.urls.small, cachedImagePath),
        this.width,
        this.height,
        UserModel(
            this.user.id,
            this.user.nickname,
            this.user.name,
            this.user.biography ?: "",
            this.user.profileImage.small,
            cachedAuthorAvatarPath
        ),
        ExifModel(
            this.exif.make ?: "Unknown",
            this.exif.model ?: "Unknown",
            this.exif.name ?: "Unknown",
            this.exif.focalLength ?: "Unknown",
            this.exif.aperture ?: "Unknown",
            this.exif.exposureTime ?: "Unknown",
            this.exif.iso ?: 0
        ),
        this.tags.map { it.title },
        LocationModel(
            this.location.city,
            this.location.country,
            this.location.name,
            this.location.position.latitude,
            this.location.position.longitude
        ),
        StatisticModel(
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

fun CollectionWithUserAndImagesEntity.toCollectionUiModel(): CollectionModel {
    return CollectionModel(
        this.collectionWithImages.collection.id,
        this.collectionWithImages.collection.title,
        this.collectionWithImages.collection.description,
        this.collectionWithImages.collection.totalPhotos,
        this.collectionWithImages.collection.cachedCoverPhoto,
        UserModel(
            this.user.id,
            this.user.nickName,
            this.user.name,
            this.user.biography,
            this.user.profileImage,
            this.user.cachedProfileImage
        )
    )
}

fun UserProfileDto.toUiModel(): ProfileModel {
    return ProfileModel(
        this.id,
        this.fullName,
        this.nickname,
        this.email,
        this.location ?: "No information",
        this.totalPhotos,
        this.totalLikes,
        this.totalCollections,
        this.downloads,
        this.biography,
        this.profileImage?.medium ?: ""
    )
}