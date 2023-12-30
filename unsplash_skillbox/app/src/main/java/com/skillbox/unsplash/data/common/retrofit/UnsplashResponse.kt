package com.skillbox.unsplash.data.common.retrofit

data class UnsplashResponse<T>(
    val result: T?,
    val errorCode: Int? = null,
    val errorText: String = ""
)