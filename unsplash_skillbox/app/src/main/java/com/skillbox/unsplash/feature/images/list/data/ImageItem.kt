package com.skillbox.unsplash.feature.images.list.data

import com.skillbox.unsplash.feature.images.commondata.Author
import com.skillbox.unsplash.feature.images.commondata.Image

data class ImageItem(
    val image: Image,
    val author: Author,
)
