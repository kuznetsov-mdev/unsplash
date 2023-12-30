package com.skillbox.unsplash.data.common.retrofit

sealed class UnsplashResult<out T> {
    data class Success<T>(val data: T) : UnsplashResult<T>()
    data class Error(val throwable: Throwable) : UnsplashResult<Nothing>()
}