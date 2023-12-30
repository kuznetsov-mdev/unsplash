package com.skillbox.unsplash.feature.profile.model

sealed class ResponseResult<out T> {
    data class Content<T>(val content: T) : ResponseResult<T>()
    data class Error(val throwable: Throwable) : ResponseResult<Nothing>()
    data object Empty : ResponseResult<Nothing>()
}