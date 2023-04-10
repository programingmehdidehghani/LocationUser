package com.example.locationservice.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.example.locationservice.util.Constants.ACTION_PAUSE_SERVICE
import com.example.locationservice.util.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.locationservice.util.Constants.NOTIFICATION_CHANNEL_ID
import com.example.locationservice.util.Constants.NOTIFICATION_CHANNEL_NAME
import com.example.locationservice.util.Constants.NOTIFICATION_ID
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ForegroundService : LifecycleService() {



    @Inject
    lateinit var baseNotificationBuilder : NotificationCompat.Builder


    override fun onCreate() {
        Log.i("service","is running")
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("service", "is running")
        startForegroundService()
        return super.onStartCommand(intent, flags, startId)
    }


    private fun startForegroundService() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }
        startForeground(NOTIFICATION_ID, baseNotificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }
}