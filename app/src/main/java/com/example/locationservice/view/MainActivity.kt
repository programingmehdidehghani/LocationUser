package com.example.locationservice.view

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.locationservice.service.LocationService
import com.example.locationservice.ui.theme.LocationServiceTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LocationServiceTheme {
                isLocationPermissionGranted(this)
            }
        }
    }
}

@SuppressLint("NewApi")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun isLocationPermissionGranted(context: Context){
    val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (!isGranted) {
           ActivityCompat.requestPermissions(
               context as Activity,
               arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
        } else{
            Intent(context,LocationService::class.java).apply {
                action = LocationService.ACTION_START
                context.startService(this)
            /*    LocationService.locationTracking.observe(this, Observer {
                    LazyColumnLocation(location = it)
                })*/
            }
        }
    }
    if ((ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)){
        SideEffect {
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }else{
        Intent(context,LocationService::class.java).apply {
            action = LocationService.ACTION_START
            context.startService(this)
           /* LocationService.locationTracking.observe(this, Observer {
                LazyColumnLocation(location = it)
            })*/
        }
    }

}








