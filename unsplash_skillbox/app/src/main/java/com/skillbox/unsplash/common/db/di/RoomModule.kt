package com.skillbox.unsplash.common.db.di

import android.content.Context
import androidx.room.Room
import com.skillbox.unsplash.common.db.MIGRATION_1_2
import com.skillbox.unsplash.common.db.MIGRATION_2_3
import com.skillbox.unsplash.common.db.MIGRATION_3_4
import com.skillbox.unsplash.common.db.MIGRATION_4_5
import com.skillbox.unsplash.common.db.MIGRATION_5_6
import com.skillbox.unsplash.common.db.MIGRATION_6_7
import com.skillbox.unsplash.common.db.UnsplashRoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

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
}