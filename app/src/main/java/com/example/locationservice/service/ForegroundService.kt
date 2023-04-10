package com.example.locationservice.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.Observer
import com.example.locationservice.util.Constants.ACTION_PAUSE_SERVICE
import com.example.locationservice.util.Constants.ACTION_START_OR_RESUME_SERVICE
import com.example.locationservice.util.Constants.ACTION_STOP_SERVICE
import com.example.locationservice.util.Constants.NOTIFICATION_CHANNEL_ID
import com.example.locationservice.util.Constants.NOTIFICATION_CHANNEL_NAME
import com.example.locationservice.util.Constants.NOTIFICATION_ID
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class ForegroundService : LifecycleService() {

    var isFirstRun = true
    var serviceKilled = false


    @Inject
    lateinit var baseNotificationBuilder : NotificationCompat.Builder

    lateinit var curNotificationBuilder : NotificationCompat.Builder

    override fun onCreate() {
        curNotificationBuilder = baseNotificationBuilder
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (intent.action) {
                ACTION_START_OR_RESUME_SERVICE -> {
                    if (isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                    } else {
                        Timber.d("resuming service ...")
                    }
                }
                ACTION_PAUSE_SERVICE -> {
                    Timber.d("paused service")
                }
                ACTION_STOP_SERVICE -> {
                    Timber.d("stoped service")
                    killService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun killService (){
        serviceKilled = true
        isFirstRun = true
        stopForeground(true)
        stopSelf()
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