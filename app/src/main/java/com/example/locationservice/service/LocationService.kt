package com.example.locationservice.service

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.locationservice.R
import com.example.locationservice.view.LocationViewModel

import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*


class LocationService : LifecycleService() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient


    override fun onBind(intent: Intent): IBinder? {
        return super.onBind(intent)
    }

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    @SuppressLint("NotificationPermission")
    private fun start(){
       val notification = NotificationCompat.Builder(this,"location")
           .setContentTitle("location user .....")
           .setContentText("Location ()")
           .setSmallIcon(R.drawable.baseline_location_on_24)
           .setOngoing(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

       locationClient.getLocationUpdate(5000L)
           .catch {  e -> e.printStackTrace()}
           .onEach { location ->
               val currentTime = System.currentTimeMillis()
               val lat = location.latitude.toString().takeLast(3)
               val long = location.longitude.toString().takeLast(3)
               Log.i("service", "lat is$lat")
               val updateNotification = notification.setContentText(
                   "Location: ($lat , $long)"
               )
               notificationManager.notify(1,updateNotification.build())
           }
           .launchIn(serviceScope)
       startForeground(1,notification.build())
    }

    private fun stop(){
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object{
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}