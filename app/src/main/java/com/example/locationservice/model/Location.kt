package com.example.locationservice.model



data class Location(
    var longitude: String,
    var latitude: String,
    var timeStamp: Long = 0L
)
