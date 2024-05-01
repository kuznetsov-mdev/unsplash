package com.skillbox.unsplash.di

import android.app.Application
import com.skillbox.unsplash.data.local.ImageLocalDataSourceApi
import com.skillbox.unsplash.data.local.datasource.ImageLocalDataSourceImpl
import com.skillbox.unsplash.data.local.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.local.storage.external.ImageInternalStorageImpl
import com.skillbox.unsplash.data.local.storage.internal.ImageExternalStorageImpl
import com.skillbox.unsplash.data.remote.ImageRemoteDataSourceApi
import com.skillbox.unsplash.data.remote.datasource.ImageRemoteDataSourceImpl
import com.skillbox.unsplash.data.remote.network.Network
import com.skillbox.unsplash.data.repository.DeviceStorageRepository
import com.skillbox.unsplash.data.repository.ImageRepositoryImpl
import com.skillbox.unsplash.domain.api.repository.ImageRepositoryApi
import com.skillbox.unsplash.domain.api.storage.ImageExternalStorage
import com.skillbox.unsplash.domain.api.storage.ImageInternalStorage
import com.skillbox.unsplash.domain.usecase.image.GetImagesUseCase
import com.skillbox.unsplash.domain.usecase.image.LikeImageUseCase
import com.skillbox.unsplash.domain.usecase.image.UnlikeImageUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ImageModule {
    @Provides
    @Singleton
    fun provideImageLocalDataSource(
        roomDatabase: UnsplashRoomDataBase
    ): ImageLocalDataSourceApi = ImageLocalDataSourceImpl(roomDatabase)

    @Provides
    @Singleton
    fun provideImageRemoteDataSource(network: Network): ImageRemoteDataSourceApi =
        ImageRemoteDataSourceImpl(network)

    @Provides
    @Singleton
    fun providesImagesRepository(
        context: Application,
        inMemory: DeviceStorageRepository,
        local: ImageLocalDataSourceApi,
        remote: ImageRemoteDataSourceApi
    ): ImageRepositoryApi = ImageRepositoryImpl(context, inMemory, local, remote)

    @Provides
    @Singleton
    fun providesImageStorage(internalStorage: ImageInternalStorage, externalStorageImpl: ImageExternalStorage): DeviceStorageRepository =
        DeviceStorageRepository(internalStorage, externalStorageImpl)

    @Provides
    @Singleton
    fun providesImageInternalStorage(context: Application): ImageInternalStorage =
        ImageInternalStorageImpl(context)

    @Provides
    @Singleton
    fun providesImageExternalStorage(context: Application, network: Network): ImageExternalStorage =
        ImageExternalStorageImpl(context, network)

    //Use cases
    @Provides
    @Singleton
    fun provideGetAllImagesUseCase(imageRepository: ImageRepositoryApi): GetImagesUseCase =
        GetImagesUseCase(imageRepository)

    @Provides
    @Singleton
    fun provideLikeImageUseCase(imageRepository: ImageRepositoryApi): LikeImageUseCase =
        LikeImageUseCase(imageRepository)

    @Provides
    @Singleton
    fun provideUnlikeImageUseCase(imageRepository: ImageRepositoryApi): UnlikeImageUseCase =
        UnlikeImageUseCase(imageRepository)

}