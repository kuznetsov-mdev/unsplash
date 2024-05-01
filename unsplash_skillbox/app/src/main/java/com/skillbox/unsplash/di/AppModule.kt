package com.skillbox.unsplash.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.skillbox.unsplash.data.local.db.MIGRATION_1_2
import com.skillbox.unsplash.data.local.db.MIGRATION_2_3
import com.skillbox.unsplash.data.local.db.MIGRATION_3_4
import com.skillbox.unsplash.data.local.db.MIGRATION_4_5
import com.skillbox.unsplash.data.local.db.MIGRATION_5_6
import com.skillbox.unsplash.data.local.db.MIGRATION_6_7
import com.skillbox.unsplash.data.local.db.UnsplashRoomDataBase
import com.skillbox.unsplash.data.remote.network.ConnectivityObserver
import com.skillbox.unsplash.data.remote.network.Network
import com.skillbox.unsplash.data.remote.network.NetworkConnectivityObserver
import com.skillbox.unsplash.data.remote.retrofit.AppRepositoryApi
import com.skillbox.unsplash.data.remote.retrofit.OnBoardingRepositoryApi
import com.skillbox.unsplash.data.repository.AppRepositoryImpl
import com.skillbox.unsplash.data.repository.DeviceStorageRepository
import com.skillbox.unsplash.data.repository.OnBoardingRepositoryImpl
import com.skillbox.unsplash.domain.api.service.AuthServiceApi
import com.skillbox.unsplash.domain.usecase.common.GetNetworkStateUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun providesUnsplashRoomDataBase(@ApplicationContext context: Context): UnsplashRoomDataBase {
        return Room.databaseBuilder(
            context,
            UnsplashRoomDataBase::class.java,
            UnsplashRoomDataBase.DB_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .addMigrations(MIGRATION_2_3)
            .addMigrations(MIGRATION_3_4)
            .addMigrations(MIGRATION_4_5)
            .addMigrations(MIGRATION_5_6)
            .addMigrations(MIGRATION_6_7)
            .build()
    }

    @Provides
    @Singleton
    fun provideConnectivityObserver(appContext: Application): ConnectivityObserver {
        return NetworkConnectivityObserver(appContext)
    }

    @Provides
    @Singleton
    fun providesAppRepository(
        dataBase: UnsplashRoomDataBase,
        internalStorage: DeviceStorageRepository
    ): AppRepositoryApi = AppRepositoryImpl(dataBase, internalStorage)

    @Provides
    @Singleton
    fun providesNetwork(authServiceApi: AuthServiceApi): Network =
        Network(authServiceApi)

    //Use Cases
    @Provides
    @Singleton
    fun provideObserveNetworkConnectionUseCase(observer: ConnectivityObserver): GetNetworkStateUseCase =
        GetNetworkStateUseCase(observer)

}

@Module
@InstallIn(ViewModelComponent::class)
interface OnBoardingRepositoryModule {
    @Binds
    @ViewModelScoped
    fun providesOnBoardingRepository(
        repository: OnBoardingRepositoryImpl
    ): OnBoardingRepositoryApi
}