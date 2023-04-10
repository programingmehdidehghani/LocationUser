package com.example.locationservice.data

import javax.inject.Inject

class LocationRepository@Inject constructor(
    private val db: LocationDatabase
) {

    suspend fun upsert (article: Location) = db.getLocationDAO().insertRun(article)

}