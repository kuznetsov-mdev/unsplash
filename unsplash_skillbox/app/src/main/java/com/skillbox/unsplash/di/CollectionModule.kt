package com.skillbox.unsplash.di

import android.app.Application
import com.skillbox.unsplash.data.local.CollectionsLocalDataSourceApi
import com.skillbox.unsplash.data.local.datasource.CollectionsLocalDataSourceImpl
import com.skillbox.unsplash.data.local.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.remote.CollectionRemoteDataSourceApi
import com.skillbox.unsplash.data.remote.datasource.CollectionRemoteDataSourceImpl
import com.skillbox.unsplash.data.remote.network.Network
import com.skillbox.unsplash.data.repository.CollectionsRepositoryImpl
import com.skillbox.unsplash.data.repository.DeviceStorageRepository
import com.skillbox.unsplash.domain.api.repository.CollectionRepositoryApi
import com.skillbox.unsplash.domain.usecase.collection.GetCollectionsUseCase
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
    fun providesCollectionLocalDataSource(dataBase: UnsplashRoomDataBase): CollectionsLocalDataSourceApi =
        CollectionsLocalDataSourceImpl(dataBase)

    @Provides
    @Singleton
    fun providesCollectionRemoteDataSource(network: Network): CollectionRemoteDataSourceApi =
        CollectionRemoteDataSourceImpl(network)

    @Provides
    @Singleton
    fun providesCollectionsRepository(
        context: Application,
        network: Network,
        deviceStorageRepository: DeviceStorageRepository,
        collectionsLocalDataSourceApi: CollectionsLocalDataSourceApi,
        collectionRemoteDataSourceApi: CollectionRemoteDataSourceApi
    ): CollectionRepositoryApi =
        CollectionsRepositoryImpl(context, deviceStorageRepository, collectionsLocalDataSourceApi, collectionRemoteDataSourceApi)

    //Use cases
    @Provides
    @Singleton
    fun providesGetCollectionsUseCase(repository: CollectionRepositoryApi): GetCollectionsUseCase =
        GetCollectionsUseCase(repository)
}