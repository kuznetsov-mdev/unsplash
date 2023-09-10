package com.skillbox.unsplash.feature.images.detail.data

import com.skillbox.unsplash.feature.images.commondata.Author
import com.skillbox.unsplash.feature.images.commondata.Image

data class DetailImageItem(
    val image: Image,
    val width: Int,
    val height: Int,
    val author: Author,
    val exif: Exif,
    val tags: List<String>,
    val location: Location,
    val statistic: Statistic,
    val downloadLink: String
)
