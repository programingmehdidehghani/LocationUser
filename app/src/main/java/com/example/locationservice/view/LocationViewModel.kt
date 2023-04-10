package com.example.locationservice.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.locationservice.data.Location
import com.example.locationservice.data.LocationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val locationRepository: LocationRepository
) : ViewModel(){


    fun insertRun(location: Location) = viewModelScope.launch {
        locationRepository.upsert(location)
    }
}