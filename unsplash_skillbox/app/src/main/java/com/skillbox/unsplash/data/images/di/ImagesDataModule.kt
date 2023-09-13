package com.skillbox.unsplash.data.images.di

import android.app.Application
import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.images.ImagesRepository
import com.skillbox.unsplash.data.images.retrofit.RetrofitImagesDataSource
import com.skillbox.unsplash.data.images.room.RoomImagesDataSource
import com.skillbox.unsplash.data.images.storage.ImageStorageDataSource
import com.skillbox.unsplash.data.images.storage.ImagesLocalDataSource
import com.skillbox.unsplash.data.images.storage.ImagesRemoteDataSource
import com.skillbox.unsplash.data.images.storage.external.ImageExternalStorage
import com.skillbox.unsplash.data.images.storage.external.ImageInternalStorageImpl
import com.skillbox.unsplash.data.images.storage.internal.ImageExternalStorageImpl
import com.skillbox.unsplash.data.images.storage.internal.ImageInternalStorage
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
        context: Application,
        inMemory: ImageStorageDataSource,
        local: ImagesLocalDataSource,
        remote: ImagesRemoteDataSource
    ): ImagesRepository = ImagesRepository(context, inMemory, local, remote)

    @Provides
    @Singleton
    fun providesImageInternalStorage(context: Application, network: Network): ImageInternalStorage =
        ImageInternalStorageImpl(context, network)

    @Provides
    @Singleton
    fun providesImageExternalStorage(context: Application, network: Network): ImageExternalStorage =
        ImageExternalStorageImpl(context, network)

    @Provides
    @Singleton
    fun providesImageStorage(internalStorage: ImageInternalStorage, externalStorageImpl: ImageExternalStorage): ImageStorageDataSource =
        ImageStorageDataSource(internalStorage, externalStorageImpl)
}