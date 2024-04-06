package com.skillbox.unsplash.data.di

import com.skillbox.unsplash.data.common.AppRepositoryImpl
import com.skillbox.unsplash.data.common.storage.DiskImageRepository
import com.skillbox.unsplash.data.local.UnsplashRoomDataBase
import com.skillbox.unsplash.data.remote.retrofit.AppRepositoryApi
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