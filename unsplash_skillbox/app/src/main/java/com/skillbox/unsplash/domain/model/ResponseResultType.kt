package com.skillbox.unsplash.domain.model

sealed class ResponseResultType<out T> {
    data class Success<T>(val content: T) : ResponseResultType<T>()
    data class Error(val throwable: Throwable) : ResponseResultType<Nothing>()
    data object Empty : ResponseResultType<Nothing>()
}