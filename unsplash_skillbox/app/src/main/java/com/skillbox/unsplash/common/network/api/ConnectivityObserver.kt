package com.skillbox.unsplash.common.network.api

import com.skillbox.unsplash.common.network.ConnectivityStatus
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<ConnectivityStatus>
}