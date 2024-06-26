package com.skillbox.unsplash.domain.model

data class ProfileModel(
    val id: String,
    val userName: String,
    val nickname: String,
    val email: String,
    val location: String,
    val totalPhotos: Int,
    val totalLikes: Int,
    val totalCollections: Int,
    val downloads: Int,
    val bio: String?,
    val profileImage: String
)
