package com.skillbox.unsplash.data.images.di

import android.app.Application
import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.images.ImagesInternalStorageDataSource
import com.skillbox.unsplash.data.images.ImagesLocalDataSource
import com.skillbox.unsplash.data.images.ImagesRemoteDataSource
import com.skillbox.unsplash.data.images.ImagesRepository
import com.skillbox.unsplash.data.images.retrofit.RetrofitImagesDataSource
import com.skillbox.unsplash.data.images.room.RoomImagesDataSource
import com.skillbox.unsplash.data.images.scopestorage.InternalStorageImagesDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ImagesDataModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(
        roomDatabase: UnsplashRoomDataBase
    ): ImagesLocalDataSource = RoomImagesDataSource(roomDatabase)

    @Provides
    @Singleton
    fun provideRemoteDataSource(network: Network): ImagesRemoteDataSource =
        RetrofitImagesDataSource(network)

    @Provides
    @Singleton
    fun providesImagesRepository(
        inMemory: ImagesInternalStorageDataSource,
        local: ImagesLocalDataSource,
        remote: ImagesRemoteDataSource
    ): ImagesRepository =
        ImagesRepository(inMemory, local, remote)

    @Provides
    @Singleton
    fun providesImagesSharedPrefsDataSource(context: Application): ImagesInternalStorageDataSource =
        InternalStorageImagesDataSource(context)
}