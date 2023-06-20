package com.skillbox.unsplash.data.images.di

import com.skillbox.unsplash.data.images.ImagesRepositoryApi
import com.skillbox.unsplash.data.images.ImagesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ImagesRepositoryModule {

    @Binds
    fun bindsImagesRepositoryModule(repository: ImagesRepositoryImpl): ImagesRepositoryApi
}