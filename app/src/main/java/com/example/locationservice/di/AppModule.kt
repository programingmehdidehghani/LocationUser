package com.example.locationservice.di

import android.content.Context
import androidx.room.Room
import com.example.locationservice.db.LocationDatabase
import com.example.locationservice.util.Constants.LOCATION_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent ::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        LocationDatabase::class.java,
        LOCATION_DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideRunDao(db : LocationDatabase) = db.getLocationDAO()
}