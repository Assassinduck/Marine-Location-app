package com.example.android_boat_exam_final.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.android_boat_exam_final.R

import com.example.android_boat_exam_final.data.Locations
import com.example.android_boat_exam_final.database.LocationDatabase
import com.example.android_boat_exam_final.database.PlacesEntity
import com.example.android_boat_exam_final.ui.list.LocationList
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val database = Room.databaseBuilder(applicationContext, LocationDatabase::class.java, "places.db").allowMainThreadQueries().fallbackToDestructiveMigration().build()

        Handler().postDelayed({
            startActivity(Intent(this, LocationList::class.java))
            finish()
        }, 2000)

        callFromWebJson(database)
    }

    private fun callFromWebJson(database: LocationDatabase) {

        val urlApi = "https://www.noforeignland.com/home/api/v1/places/"

        val client = OkHttpClient()

        val request = Request.Builder().url(urlApi).build()

        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()

                val gSon = GsonBuilder().create()

                val location = gSon.fromJson(responseBody, Locations::class.java)

                if(database.locationsDAO().getAllPlaces().isEmpty()){

                    location.features.forEach{

                        val featureThread = Thread{

                            val locationEntity = PlacesEntity()
                            locationEntity.placesId = it.properties.id
                            locationEntity.placeName = it.properties.name
                            locationEntity.placeLon = it.geometry.coordinates[0]
                            locationEntity.placeLat = it.geometry.coordinates[1]

                            database.locationsDAO().insertPlaces(locationEntity)
                        }
                        featureThread.start()
                    }
                }else {
                    Log.d("database", "Fetching to locale storage")
                }
                Log.d("database", "Fetching from API")

            }

            override fun onFailure(call: Call, e: IOException) {

                println("Failed to fetch, JSON")
            }
        })
    }


}
