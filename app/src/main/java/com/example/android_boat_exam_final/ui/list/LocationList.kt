package com.example.android_boat_exam_final.ui.list


import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.android_boat_exam_final.R

import com.example.android_boat_exam_final.database.LocationDatabase
import com.example.android_boat_exam_final.database.PlacesEntity
import kotlinx.android.synthetic.main.list_locations.*

class LocationList : AppCompatActivity() {

    private var adapterLocation: LocationListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_locations)

        val database = Room.databaseBuilder(applicationContext, LocationDatabase::class.java, "places.db").allowMainThreadQueries().fallbackToDestructiveMigration().build()

        val listOfLocations = database.locationsDAO().getAllPlaces()

        rV_List.layoutManager = LinearLayoutManager(this)

        renderLocations(listOfLocations)

    }

    private fun renderLocations(listOfLocations: List<PlacesEntity>) {

        runOnUiThread {
            adapterLocation = LocationListAdapter(listOfLocations as MutableList<PlacesEntity>)
            rV_List.adapter = adapterLocation
            adapterLocation!!.notifyDataSetChanged()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {


        menuInflater.inflate(R.menu.main_menu, menu)

        val searchItem: MenuItem = menu.findItem(R.id.menu_Search)
        val searchView: SearchView = searchItem.actionView as SearchView

        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapterLocation?.filter?.filter(newText)
                return false
            }
        })
        return true
    }
}




