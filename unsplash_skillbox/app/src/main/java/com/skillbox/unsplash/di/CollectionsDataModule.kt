package com.skillbox.unsplash.di

import com.skillbox.unsplash.data.impl.RetrofitRetrofitCollectionsRepositoryImpl
import com.skillbox.unsplash.data.impl.RoomCollectionsRepositoryImpl
import com.skillbox.unsplash.data.local.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.remote.network.Network
import com.skillbox.unsplash.domain.api.repository.RetrofitCollectionsRepositoryApi
import com.skillbox.unsplash.domain.api.repository.RoomCollectionsRepositoryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CollectionsDataModule {

    @Provides
    @Singleton
    fun providesRetrofitCollectionRepository(network: Network): RetrofitCollectionsRepositoryApi =
        RetrofitRetrofitCollectionsRepositoryImpl(network)

    @Provides
    @Singleton
    fun providesCollectionRoomRepository(dataBase: UnsplashRoomDataBase): RoomCollectionsRepositoryApi =
        RoomCollectionsRepositoryImpl(dataBase)
}