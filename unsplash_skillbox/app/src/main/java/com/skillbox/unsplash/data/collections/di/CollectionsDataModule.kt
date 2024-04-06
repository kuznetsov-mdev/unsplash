package com.skillbox.unsplash.data.collections.di

import com.skillbox.unsplash.data.collections.retrofit.RetrofitCollectionsRepositoryApi
import com.skillbox.unsplash.data.collections.retrofit.RetrofitRetrofitCollectionsRepositoryImpl
import com.skillbox.unsplash.data.collections.room.RoomCollectionsRepositoryApi
import com.skillbox.unsplash.data.collections.room.RoomCollectionsRepositoryImpl
import com.skillbox.unsplash.data.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.network.Network
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