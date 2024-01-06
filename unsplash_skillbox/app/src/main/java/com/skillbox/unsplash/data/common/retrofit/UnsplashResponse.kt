package com.skillbox.unsplash.data.common.retrofit

sealed class UnsplashResponse<out T> {
    data class Success<T>(val data: T) : UnsplashResponse<T>()
    data class Error(val throwable: Throwable) : UnsplashResponse<Nothing>()
}