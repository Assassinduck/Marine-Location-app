package com.example.android_boat_exam_final.ui.details

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_boat_exam_final.R
import com.example.android_boat_exam_final.data.FromLocationId
import com.example.android_boat_exam_final.ui.list.LocationListAdapter

import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.list_locations.*
import okhttp3.*
import java.io.IOException

class LocationDetails : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.list_locations)

        rV_List.layoutManager = LinearLayoutManager(this)

        val navBarTitle = intent.getStringExtra(LocationListAdapter.LocationListViewHolder.LOCATION_TITLE_KEY)

        supportActionBar?.title = navBarTitle

        queryWebApiJSON()
    }

    private fun queryWebApiJSON() {

        val locationId = intent.getLongExtra(LocationListAdapter.LocationListViewHolder.LOCATION_ID_KEY, -1)
        val locationApiUrl = "https://www.noforeignland.com/home/api/v1/place?id=$locationId"

        val client = OkHttpClient()


        val request = Request.Builder().url(locationApiUrl).build()


        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val fromLocationId = gson.fromJson(body, FromLocationId::class.java)

                runOnUiThread {
                    rV_List.adapter =
                        LocationsDetailsAdapter(
                            fromLocationId
                        )
                }

            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to fetch the api")
            }
        })
    }
}