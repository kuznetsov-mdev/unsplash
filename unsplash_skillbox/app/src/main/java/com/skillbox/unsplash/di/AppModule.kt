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
import com.skillbox.unsplash.data.local.storage.external.ImageInternalStorageImpl
import com.skillbox.unsplash.data.local.storage.internal.ImageExternalStorageImpl
import com.skillbox.unsplash.data.remote.network.ConnectivityObserver
import com.skillbox.unsplash.data.remote.network.Network
import com.skillbox.unsplash.data.remote.network.NetworkConnectivityObserver
import com.skillbox.unsplash.data.remote.retrofit.AppRepositoryApi
import com.skillbox.unsplash.data.remote.retrofit.AuthRepositoryApi
import com.skillbox.unsplash.data.remote.retrofit.OnBoardingRepositoryApi
import com.skillbox.unsplash.data.repository.AppRepositoryImpl
import com.skillbox.unsplash.data.repository.AuthRepositoryImpl
import com.skillbox.unsplash.data.repository.DiskImageRepository
import com.skillbox.unsplash.data.repository.ImageRepositoryImpl
import com.skillbox.unsplash.data.repository.OnBoardingRepositoryImpl
import com.skillbox.unsplash.data.repository.RetrofitImageRepositoryImpl
import com.skillbox.unsplash.data.repository.RetrofitProfileRepositoryImpl
import com.skillbox.unsplash.data.repository.RoomCollectionsRepositoryImpl
import com.skillbox.unsplash.data.repository.RoomImageRepositoryImpl
import com.skillbox.unsplash.data.service.AuthServiceImpl
import com.skillbox.unsplash.domain.api.repository.ImageRepositoryApi
import com.skillbox.unsplash.domain.api.repository.RetrofitImageRepositoryApi
import com.skillbox.unsplash.domain.api.repository.RetrofitProfileRepositoryApi
import com.skillbox.unsplash.domain.api.repository.RoomCollectionsRepositoryApi
import com.skillbox.unsplash.domain.api.repository.RoomImageRepositoryApi
import com.skillbox.unsplash.domain.api.service.AuthServiceApi
import com.skillbox.unsplash.domain.api.storage.ImageExternalStorage
import com.skillbox.unsplash.domain.api.storage.ImageInternalStorage
import com.skillbox.unsplash.domain.usecase.common.GetNetworkStateUseCase
import com.skillbox.unsplash.domain.usecase.image.GetImagesUseCase
import com.skillbox.unsplash.domain.usecase.image.LikeImageUseCase
import com.skillbox.unsplash.domain.usecase.image.UnlikeImageUseCase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import net.openid.appauth.AuthorizationService
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
    fun providesAuthorizationService(context: Application): AuthorizationService =
        AuthorizationService(context)

    @Provides
    @Singleton
    fun providesAuthRepository(authService: AuthServiceApi): AuthRepositoryApi =
        AuthRepositoryImpl(authService)

    @Provides
    @Singleton
    fun providesAppRepository(
        dataBase: UnsplashRoomDataBase,
        internalStorage: DiskImageRepository
    ): AppRepositoryApi = AppRepositoryImpl(dataBase, internalStorage)

    @Provides
    @Singleton
    fun providesCollectionRoomRepository(dataBase: UnsplashRoomDataBase): RoomCollectionsRepositoryApi =
        RoomCollectionsRepositoryImpl(dataBase)

    @Provides
    @Singleton
    fun providesAuthService(authorizationService: AuthorizationService): AuthServiceApi =
        AuthServiceImpl(authorizationService)

    @Provides
    @Singleton
    fun providesNetwork(authServiceApi: AuthServiceApi): Network =
        Network(authServiceApi)

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
    ): ImageRepositoryApi = ImageRepositoryImpl(context, inMemory, local, remote)

    @Provides
    @Singleton
    fun providesImageInternalStorage(context: Application): ImageInternalStorage =
        ImageInternalStorageImpl(context)

    @Provides
    @Singleton
    fun providesImageExternalStorage(context: Application, network: Network): ImageExternalStorage =
        ImageExternalStorageImpl(context, network)

    @Provides
    @Singleton
    fun providesImageStorage(internalStorage: ImageInternalStorage, externalStorageImpl: ImageExternalStorage): DiskImageRepository =
        DiskImageRepository(internalStorage, externalStorageImpl)

    @Provides
    @Singleton
    fun providesRetrofitAccountRepository(network: Network): RetrofitProfileRepositoryApi =
        RetrofitProfileRepositoryImpl(network)

    //Use Cases
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