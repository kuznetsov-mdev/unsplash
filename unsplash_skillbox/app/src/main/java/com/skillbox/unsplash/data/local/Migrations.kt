package com.skillbox.unsplash.data.local

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import timber.log.Timber

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        try {
            database.execSQL("ALTER TABLE images ADD COLUMN description TEXT NOT NULL DEFAULT ''")
            database.execSQL("CREATE INDEX IF NOT EXISTS index_images_description ON images(description)")
        } catch (t: Throwable) {
            Timber.e(t.message)
        }
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        try {
            database.execSQL("ALTER TABLE images ADD COLUMN cached_preview TEXT NOT NULL DEFAULT ''")
            database.execSQL("ALTER TABLE images ADD COLUMN cached_profile_image TEXT NOT NULL DEFAULT ''")
        } catch (t: Throwable) {
            Timber.e(t.message)
        }
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
        try {
            database.execSQL("ALTER TABLE authors ADD COLUMN biography TEXT NOT NULL DEFAULT ''")
        } catch (t: Throwable) {
            Timber.e(t.message)
        }
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        try {
            database.execSQL("ALTER TABLE images ADD COLUMN search_query TEXT NOT NULL DEFAULT ''")
        } catch (t: Throwable) {
            Timber.e(t.message)
        }
    }
}

val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `collections` " +
                    "(`id` TEXT NOT NULL, " +
                    "`author_id` TEXT NOT NULL, " +
                    "`title` TEXT NOT NULL, " +
                    "`description` TEXT NOT NULL, " +
                    "`published_at` TEXT NOT NULL, " +
                    "`updated_at` TEXT NOT NULL, " +
                    "`total_photos` INTEGER NOT NULL, " +
                    "`cached_cover_photo` TEXT NOT NULL, " +
                    "PRIMARY KEY(`id`))"
        )
    }
}

val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "CREATE TABLE IF NOT EXISTS `collection_image` " +
                    "(`collection_id` TEXT NOT NULL, " +
                    "`image_id` TEXT NOT NULL, " +
                    "PRIMARY KEY(`collection_id`, `image_id`))"
        )
    }
}