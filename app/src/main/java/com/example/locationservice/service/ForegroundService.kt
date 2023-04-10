package com.example.locationservice.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.locationservice.util.Constants.NOTIFICATION_CHANNEL_ID
import com.example.locationservice.util.Constants.NOTIFICATION_CHANNEL_NAME
import com.example.locationservice.util.Constants.NOTIFICATION_ID
import com.google.android.gms.location.LocationListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.Flow
import javax.inject.Inject


@AndroidEntryPoint
class ForegroundService : LifecycleService(), LocationListener {



    @Inject
    lateinit var baseNotificationBuilder : NotificationCompat.Builder

    private val timer: Timer? = null

    var locationManager: LocationManager? = null
    var gpsLocationListener: LocationListener? = null
    override fun onCreate() {
        Log.i("service","is running")
        super.onCreate()
    }

    @SuppressLint("LogNotTimber")
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

    override fun onLocationChanged(p0: Location) {
        TODO("Not yet implemented")
    }
}