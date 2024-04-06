package com.skillbox.unsplash.data.network.api

import com.skillbox.unsplash.data.network.ConnectivityStatus
import kotlinx.coroutines.flow.Flow

interface ConnectivityObserver {

    fun observe(): Flow<ConnectivityStatus>
}