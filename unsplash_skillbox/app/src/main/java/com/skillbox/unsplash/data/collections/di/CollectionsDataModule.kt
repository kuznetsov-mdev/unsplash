package com.skillbox.unsplash.data.collections.di

import com.skillbox.unsplash.common.network.Network
import com.skillbox.unsplash.data.collections.retrofit.RetrofitCollectionsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CollectionsDataModule {

    @Provides
    @Singleton
    fun providesRetrofitCollectionRepository(network: Network) = RetrofitCollectionsRepositoryImpl(network)
}