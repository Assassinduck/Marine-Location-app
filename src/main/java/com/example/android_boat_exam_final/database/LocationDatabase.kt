package com.example.android_boat_exam_final.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [(PlacesEntity::class)],
    version = 1,
    exportSchema = false)
abstract class LocationDatabase: RoomDatabase() {

    abstract fun locationsDAO(): LocationsDAO


}