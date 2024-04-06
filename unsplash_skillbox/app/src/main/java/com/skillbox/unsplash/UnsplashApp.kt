package com.skillbox.unsplash

import android.app.Application
import android.content.Context
import android.os.StrictMode
import android.util.Log
import androidx.work.Configuration
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.android.utils.FlipperUtils
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.skillbox.unsplash.data.common.service.DownloadFactory
import com.skillbox.unsplash.data.network.Network
import com.skillbox.unsplash.data.notification.NotificationChannels
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class UnsplashApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: DownloadFactory

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        SoLoader.init(this, false)
        createDownloadChannel(this)

        if (BuildConfig.DEBUG && FlipperUtils.shouldEnableFlipper(this)) {
            AndroidFlipperClient.getInstance(this).apply {
                addPlugin(InspectorFlipperPlugin(this@UnsplashApp, DescriptorMapping.withDefaults()))
                addPlugin(Network.NETWORK_FLIPPER_PLUGIN)
                addPlugin(DatabasesFlipperPlugin(this@UnsplashApp))
            }.start()
        }

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
//                    .detectDiskReads()
//                    .detectDiskWrites()
                    .detectNetwork()
                    .penaltyLog()
//                    .penaltyDeath()
                    .build()
            )
        }
    }

    private fun createDownloadChannel(context: Context) {
        NotificationChannels.create(context)
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()
}