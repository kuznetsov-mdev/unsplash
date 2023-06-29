package com.skillbox.unsplash.data.images.di

import com.skillbox.unsplash.data.images.ImageListRepositoryApi
import com.skillbox.unsplash.data.images.ImageListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ImageListRepositoryModule {

    @Binds
    fun bindsImagesRepositoryModule(repository: ImageListRepositoryImpl): ImageListRepositoryApi
}