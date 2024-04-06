package com.skillbox.unsplash.data.images.di

import android.app.Application
import com.skillbox.unsplash.data.common.storage.DiskImageRepository
import com.skillbox.unsplash.data.common.storage.external.ImageExternalStorage
import com.skillbox.unsplash.data.common.storage.external.ImageInternalStorageImpl
import com.skillbox.unsplash.data.common.storage.internal.ImageExternalStorageImpl
import com.skillbox.unsplash.data.common.storage.internal.ImageInternalStorage
import com.skillbox.unsplash.data.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.images.ImageRepository
import com.skillbox.unsplash.data.images.retrofit.RetrofitImageRepositoryApi
import com.skillbox.unsplash.data.images.retrofit.RetrofitImageRepositoryApiImpl
import com.skillbox.unsplash.data.images.room.RoomImageRepositoryApi
import com.skillbox.unsplash.data.images.room.RoomImageRepositoryImpl
import com.skillbox.unsplash.data.network.Network
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
    ): RoomImageRepositoryApi = RoomImageRepositoryImpl(roomDatabase)

    @Provides
    @Singleton
    fun provideRemoteDataSource(network: Network): RetrofitImageRepositoryApi =
        RetrofitImageRepositoryApiImpl(network)

    @Provides
    @Singleton
    fun providesImagesRepository(
        context: Application,
        inMemory: DiskImageRepository,
        local: RoomImageRepositoryApi,
        remote: RetrofitImageRepositoryApi
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