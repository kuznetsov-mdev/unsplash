package com.skillbox.unsplash.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Build
import com.skillbox.unsplash.data.network.api.ConnectivityObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class NetworkConnectivityObserver @Inject constructor(
    context: Context
) : ConnectivityObserver {

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun observe(): Flow<ConnectivityStatus> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    launch {
                        Timber.d("Current thread is ${Thread.currentThread().name}")
                        send(ConnectivityStatus.Available)
                    }
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    super.onLosing(network, maxMsToLive)
                    launch {
                        Timber.d("Current thread is ${Thread.currentThread().name}")
                        send(ConnectivityStatus.Losing)
                    }
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    launch {
                        Timber.d("Current thread is ${Thread.currentThread().name}")
                        send(ConnectivityStatus.Lost)
                    }
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    launch {
                        Timber.d("Current thread is ${Thread.currentThread().name}")
                        send(ConnectivityStatus.Unavailable)
                    }
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(callback)
            }
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }
            .flowOn(Dispatchers.IO)
            .distinctUntilChanged()

    }
}