package com.skillbox.unsplash.domain.usecase.common

import com.skillbox.unsplash.data.remote.network.ConnectivityObserver
import com.skillbox.unsplash.data.remote.network.ConnectivityStatus
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNetworkStateUseCase @Inject constructor(
    private val connectivityObserver: ConnectivityObserver
) {
    operator fun invoke(): Flow<ConnectivityStatus> {
        return connectivityObserver.observe()
    }
}