package com.example.locationservice.db

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

interface LocationDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(location: Location)

    @Query("SELECT * FROM location")
    fun getAllRunsSortedByDate() : LiveData<List<Location>>
}