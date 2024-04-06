package com.skillbox.unsplash.data.local.contract

object ImageDetailContract {
    const val TABLE_NAME = "image_details"

    object Columns {
        const val ID = "id"
        const val IMAGE_ID = "image_id"
        const val CAMERA_MAKER = "camera_maker"
        const val CAMERA_MODEL = "camera_model"
        const val EXPOSURE = "exposure"
        const val APERTURE = "aperture"
        const val FOCAL_LENGTH = "focal_length"
        const val ISO = "iso"
        const val COUNTRY = "country"
        const val CITY = "city"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
    }
}