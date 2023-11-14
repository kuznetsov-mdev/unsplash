package com.skillbox.unsplash.data.collections.di

import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.collections.retrofit.RetrofitCollectionsRepositoryApi
import com.skillbox.unsplash.data.collections.retrofit.RetrofitRetrofitCollectionsRepositoryImpl
import com.skillbox.unsplash.data.collections.room.RoomCollectionsRepository
import com.skillbox.unsplash.data.collections.room.RoomCollectionsRepositoryImpl
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
    fun providesCollectionRoomRepository(dataBase: UnsplashRoomDataBase): RoomCollectionsRepository =
        RoomCollectionsRepositoryImpl(dataBase)
}