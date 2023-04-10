package com.example.locationservice.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location")
data class Location(
    @PrimaryKey(autoGenerate = true)
    var id : Int? = null,
    var longitude : Double,
    var latitude : Double,
    var timeStamp : Long = 0L
)
