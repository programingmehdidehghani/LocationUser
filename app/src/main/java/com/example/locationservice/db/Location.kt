package com.example.locationservice.db

import androidx.room.Entity

@Entity(tableName = "location")
data class Location(
    var longitude : Double,
    var latitude : Double,
    var timeStamp : Long = 0L
)
