package com.skillbox.unsplash.common

sealed class LoadState<out T> {
    data object Loading : LoadState<Nothing>()
    data class Success<T>(val result: T) : LoadState<T>()
    data class Error(val reason: String) : LoadState<Nothing>()
}