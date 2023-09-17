package com.skillbox.unsplash.common.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationManagerCompat

object NotificationChannels {
    const val DOWNLOAD_CHANNEL_ID = "Downloading"

    fun create(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createDownloadImageChannel(context)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createDownloadImageChannel(context: Context) {
        val downloadMsg = "Message"
        val desc = "Download image"
        val priority = NotificationManager.IMPORTANCE_LOW

        NotificationChannel(DOWNLOAD_CHANNEL_ID, downloadMsg, priority).apply {
            description = desc
            NotificationManagerCompat.from(context)
                .createNotificationChannel(this)
        }
    }
}