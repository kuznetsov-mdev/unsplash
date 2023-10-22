package com.skillbox.unsplash.common.extensions

import com.skillbox.unsplash.data.images.retrofit.model.image.RemoteImage
import com.skillbox.unsplash.data.images.retrofit.model.image.detail.RemoteImageDetail
import com.skillbox.unsplash.data.images.room.model.AuthorEntity
import com.skillbox.unsplash.data.images.room.model.ImageEntity
import com.skillbox.unsplash.data.images.room.model.relations.ImageWithAuthorEntity
import com.skillbox.unsplash.feature.images.commondata.Author
import com.skillbox.unsplash.feature.images.commondata.Image
import com.skillbox.unsplash.feature.images.detail.data.DetailImageItem
import com.skillbox.unsplash.feature.images.detail.data.Exif
import com.skillbox.unsplash.feature.images.detail.data.Location
import com.skillbox.unsplash.feature.images.detail.data.Statistic
import com.skillbox.unsplash.feature.images.list.data.ImageItem

//fun RemoteImage.toImageItem(cachedImagePath: String, cachedAvatarPath: String): ImageItem {
//    return ImageItem(
//        Image(
//            this.id,
//            this.description ?: "",
//            this.likes,
//            this.likedByUser,
//            this.urls.small,
//            cachedImagePath
//        ),
//        Author(
//            this.user.id,
//            this.user.nickname,
//            this.user.name,
//            this.user.biography ?: "",
//            this.user.profileImage.small,
//            cachedAvatarPath
//        )
//    )
//}

fun RemoteImage.toRoomImageEntity(cachedImagePath: String, cachedAvatarPath: String, searchQuery: String): ImageWithAuthorEntity {
    val authorEntity = AuthorEntity(
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
    return ImageWithAuthorEntity(imageEntity, authorEntity)
}

fun ImageWithAuthorEntity.toImageItem(): ImageItem {
    return ImageItem(
        Image(
            this.image.id,
            this.image.description,
            this.image.likes,
            this.image.likedByUser,
            this.image.preview,
            this.image.cachedPreview
        ),
        Author(
            this.author.id,
            this.author.nickName,
            this.author.name,
            this.author.biography,
            this.author.profileImage,
            this.author.cachedProfileImage
        )
    )
}

fun RemoteImageDetail.toDetailImageItem(cachedImagePath: String, cachedAuthorAvatarPath: String): DetailImageItem {
    return DetailImageItem(
        Image(this.id, this.description ?: "", this.likes, this.likedByUser, this.urls.small, cachedImagePath),
        this.width,
        this.height,
        Author(
            this.user.id,
            this.user.nickname,
            this.user.name,
            this.user.biography ?: "",
            this.user.profileImage.small,
            cachedAuthorAvatarPath
        ),
        Exif(
            this.exif.make ?: "Unknown",
            this.exif.model ?: "Unknown",
            this.exif.name ?: "Unknown",
            this.exif.focalLength ?: "Unknown",
            this.exif.aperture ?: "Unknown",
            this.exif.exposureTime ?: "Unknown",
            this.exif.iso ?: 0
        ),
        this.tags.map { it.title },
        Location(
            this.location.city,
            this.location.country,
            this.location.name,
            this.location.position.latitude,
            this.location.position.longitude
        ),
        Statistic(
            this.downloads,
            0,
            this.likes
        ),
        this.links.download
    )
}