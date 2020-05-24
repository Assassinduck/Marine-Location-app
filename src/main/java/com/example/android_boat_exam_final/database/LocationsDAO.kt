package com.example.android_boat_exam_final.database

import androidx.room.*

@Dao
interface LocationsDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlaces(places: PlacesEntity)

    @Query(value = "SELECT * FROM PlacesEntity")
    fun getAllPlaces(): List<PlacesEntity>

    @Query(value = "SELECT * FROM PlacesEntity WHERE placesId LIKE :id")
    fun getPlacesById(id : Long): List<PlacesEntity>

    @Query(value = "SELECT placesId FROM PlacesEntity")
    fun getAllPlacesIds(): List<Long>

    @Update
    fun updatePlace(vararg place: PlacesEntity)

    @Query(value = "UPDATE PlacesEntity SET placesId = :newId WHERE placesId LIKE :id")
    fun updatePlaceWithQuery(id: Long, newId: Long)



}