package com.skillbox.unsplash.data.di

import android.app.Application
import com.skillbox.unsplash.data.common.storage.DiskImageRepository
import com.skillbox.unsplash.data.common.storage.external.ImageInternalStorageImpl
import com.skillbox.unsplash.data.common.storage.internal.ImageExternalStorageImpl
import com.skillbox.unsplash.data.impl.ImageRepository
import com.skillbox.unsplash.data.impl.RetrofitImageRepositoryImpl
import com.skillbox.unsplash.data.impl.RoomImageRepositoryImpl
import com.skillbox.unsplash.data.local.UnsplashRoomDataBase
import com.skillbox.unsplash.data.remote.network.Network
import com.skillbox.unsplash.domain.api.repository.RetrofitImageRepositoryApi
import com.skillbox.unsplash.domain.api.repository.RoomImageRepositoryApi
import com.skillbox.unsplash.domain.api.storage.ImageExternalStorage
import com.skillbox.unsplash.domain.api.storage.ImageInternalStorage
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
        RetrofitImageRepositoryImpl(network)

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