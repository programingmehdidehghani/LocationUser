package com.example.locationservice.model

class LocationRepository(
    private val db: LocationDatabase
) {

    suspend fun upsert (article: Location) = db.getLocationDAO().insertRun(article)

}