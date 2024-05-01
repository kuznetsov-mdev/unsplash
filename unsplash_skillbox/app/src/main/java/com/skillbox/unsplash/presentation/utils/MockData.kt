package com.skillbox.unsplash.presentation.utils

import com.skillbox.unsplash.common.LoadState
import com.skillbox.unsplash.domain.model.ImageModel
import com.skillbox.unsplash.domain.model.UserModel
import com.skillbox.unsplash.domain.model.detail.ExifModel
import com.skillbox.unsplash.domain.model.detail.ImageDetailModel
import com.skillbox.unsplash.domain.model.detail.LocationModel
import com.skillbox.unsplash.domain.model.detail.StatisticModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object MockData {
    fun getImageDetail(): StateFlow<LoadState<ImageDetailModel>> {
        return MutableStateFlow(
            LoadState.Success(
                result =
                ImageDetailModel(
                    image = ImageModel(
                        id = "Mu39DUwHhP4",
                        description = "",
                        likes = 12,
                        likedByUser = false,
                        url = "https://images.unsplash.com/photo-1714329781250-64b9125f689c?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=M3wzNzc5MTN8MHwxfGFsbHx8fHx8fHx8fDE3MTQ1Njc1Mzh8&ixlib=rb-4.0.3&q=80&w=400",
                        cachedLocation = "stub"
                    ),
                    width = 5120,
                    height = 2880,
                    author = UserModel(
                        id = "7pAZ3nPo0eE",
                        nickname = "misterlindstrom",
                        name = "Micke Lindström",
                        biography = "I like to make 3D stuff",
                        avatarUrl = "https://images.unsplash.com/profile-1710810631452-ac77983a46b8image?ixlib=rb-4.0.3&crop=faces&fit=crop&w=32&h=32",
                        cachedAvatarLocation = "stub"
                    ),
                    exif = ExifModel(
                        make = "Unknown",
                        model = "Unknown",
                        name = "Unknown",
                        focalLength = "0.0",
                        aperture = "Unknown",
                        exposureTime = "Unknown",
                        iso = 0
                    ),
                    tags = listOf(
                        "desert",
                        "sand",
                        "wallpaper",
                        "background image",
                        "background",
                        "night",
                        "sunset",
                        "cliff",
                        "rocks",
                        "balancing rocks",
                        "digital render",
                        "render",
                        "3d render",
                        "outdoors",
                        "nature",
                        "rock",
                        "arched",
                        "arch",
                        "human",
                        "person"
                    ),
                    location = LocationModel(
                        city = null,
                        country = null,
                        name = null,
                        latitude = 0.0,
                        longitude = 0.0
                    ),
                    statistic = StatisticModel(
                        downloads = 23,
                        views = 0,
                        likes = 12
                    ),
                    downloadLink = "https://unsplash.com/photos/Mu39DUwHhP4/download?ixid=M3wzNzc5MTN8MHwxfGFsbHx8fHx8fHx8fDE3MTQ1Njc1Mzh8"
                )
            )
        ).asStateFlow()
    }

}

