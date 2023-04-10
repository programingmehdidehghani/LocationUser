package com.example.locationservice.presention

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.locationservice.service.ForegroundService
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

@Composable
fun isLocationPermissionGranted(context: Context){
    val requestLocationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (!isGranted) {
           ActivityCompat.requestPermissions(
               context as Activity,
               arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),1)
        } else{
            Intent(context,ForegroundService::class.java).apply {
                action = ForegroundService.ACTION_START
                context.startService(this)
            }
        }
    }
    if ((ContextCompat.checkSelfPermission(
            context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED)){
        SideEffect {
            requestLocationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }else{
        Intent(context,ForegroundService::class.java).apply {
            action = ForegroundService.ACTION_START
            context.startService(this)
        }
    }

}








