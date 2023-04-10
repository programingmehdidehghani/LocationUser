package com.example.locationservice.service

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationClient {
    fun getLocationUpdate(interval: Long): Flow<Location>

    class LocationException(message: String) : java.lang.Exception()
}