package com.skillbox.unsplash.presentation.utils

import androidx.compose.runtime.Composable
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.skillbox.unsplash.presentation.components.common.EmptyScreen

@Composable
fun <T : Any> LazyPagingItems<T>.handlePagingResult(
    onRepeatClick: () -> Unit,
    shimmerEffect: @Composable () -> Unit
): Boolean {
    val loadState = this.loadState

    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            shimmerEffect()
            return false
        }

        error != null -> {
            EmptyScreen(
                error = error,
                onRepeatClick = onRepeatClick
            )
            false
        }

        this.itemCount == 0 -> {
            EmptyScreen(onRepeatClick = onRepeatClick)
            false
        }

        else -> true
    }
}