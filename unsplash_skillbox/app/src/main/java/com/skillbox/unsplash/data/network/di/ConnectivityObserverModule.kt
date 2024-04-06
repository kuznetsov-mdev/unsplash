package com.skillbox.unsplash.data.network.di

import android.app.Application
import com.skillbox.unsplash.data.network.NetworkConnectivityObserver
import com.skillbox.unsplash.data.network.api.ConnectivityObserver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ConnectivityObserverModule {

    @Provides
    @Singleton
    fun provideConnectivityObserver(appContext: Application): ConnectivityObserver {
        return NetworkConnectivityObserver(appContext)
    }
}