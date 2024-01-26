package com.skillbox.unsplash.data.common.di

import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.common.AppRepositoryApi
import com.skillbox.unsplash.data.common.AppRepositoryImpl
import com.skillbox.unsplash.data.common.storage.DiskImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDataModule {

    @Provides
    @Singleton
    fun providesAppRepository(
        dataBase: UnsplashRoomDataBase,
        internalStorage: DiskImageRepository
    ): AppRepositoryApi = AppRepositoryImpl(dataBase, internalStorage)
}