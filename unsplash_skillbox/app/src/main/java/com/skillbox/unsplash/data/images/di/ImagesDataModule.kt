package com.skillbox.unsplash.data.images.di

import android.app.Application
import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.images.ImageRepository
import com.skillbox.unsplash.data.images.retrofit.RetrofitImageRepository
import com.skillbox.unsplash.data.images.retrofit.RetrofitImageRepositoryImpl
import com.skillbox.unsplash.data.images.room.RoomImageRepository
import com.skillbox.unsplash.data.images.room.RoomImageRepositoryImpl
import com.skillbox.unsplash.data.images.storage.DiskImageRepository
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
    ): RoomImageRepository = RoomImageRepositoryImpl(roomDatabase)

    @Provides
    @Singleton
    fun provideRemoteDataSource(network: Network): RetrofitImageRepository =
        RetrofitImageRepositoryImpl(network)

    @Provides
    @Singleton
    fun providesImagesRepository(
        context: Application,
        inMemory: DiskImageRepository,
        local: RoomImageRepository,
        remote: RetrofitImageRepository
    ): ImageRepository = ImageRepository(context, inMemory, local, remote)

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
    fun providesImageStorage(internalStorage: ImageInternalStorage, externalStorageImpl: ImageExternalStorage): DiskImageRepository =
        DiskImageRepository(internalStorage, externalStorageImpl)
}