package com.example.android_boat_exam_final.ui.map

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.android_boat_exam_final.R

import com.example.android_boat_exam_final.ui.details.LocationsDetailsAdapter

import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class LocationMapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private lateinit var gmap: GoogleMap

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private fun setUpGMap() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val gMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        gMapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)



    }

    /**
     * Manipulates the gmap once available.
     * This callback is triggered when the gmap is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */

    override fun onMapReady(googleMap: GoogleMap) {
        val longitude = intent.getDoubleExtra(LocationsDetailsAdapter.LocationDetailsViewHolder.LOCATION_LON, 1.0)
        val latitude = intent.getDoubleExtra(LocationsDetailsAdapter.LocationDetailsViewHolder.LOCATION_LAT, 1.0)

        gmap = googleMap

        val myLocation = LatLng(latitude, longitude)
        gmap.addMarker(MarkerOptions().position(myLocation).title("My Favorite City/Dummy"))
        gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 15.0f))

        gmap.uiSettings.isZoomControlsEnabled = true
        gmap.setOnMarkerClickListener(this)

        setUpGMap()

    }

    override fun onMarkerClick(p0: Marker?) = false

}