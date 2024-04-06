package com.skillbox.unsplash.di

import com.skillbox.unsplash.data.local.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.repository.RoomCollectionsRepositoryImpl
import com.skillbox.unsplash.domain.api.repository.RoomCollectionsRepositoryApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CollectionModule {

    @Provides
    @Singleton
    fun providesCollectionRoomRepository(dataBase: UnsplashRoomDataBase): RoomCollectionsRepositoryApi =
        RoomCollectionsRepositoryImpl(dataBase)
}