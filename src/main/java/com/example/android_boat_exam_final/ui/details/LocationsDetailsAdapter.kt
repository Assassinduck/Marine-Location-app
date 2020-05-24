package com.example.android_boat_exam_final.ui.details

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_boat_exam_final.R
import com.example.android_boat_exam_final.data.FromLocationId

import com.example.android_boat_exam_final.ui.map.LocationMapsActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.location_details_row.view.*

class LocationsDetailsAdapter(val fromLocationId: FromLocationId) : RecyclerView.Adapter<LocationsDetailsAdapter.LocationDetailsViewHolder>() {


    class LocationDetailsViewHolder(val customViewHolder: View, var fromLocationId: FromLocationId? = null) : RecyclerView.ViewHolder(customViewHolder) {

        companion object {
            const val LOCATION_ID_KEY = "LOCATION_ID"
            const val LOCATION_LAT = "LOCATION_LAT"
            const val LOCATION_LON = "LOCATION_LON"
        }

    }

    override fun getItemCount(): Int {
        return 1
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewholder: LocationDetailsViewHolder, position: Int) {

        val location = fromLocationId.place

        val defaultComment = "No comments has been made yet of this place."


        val locationComments = location.comments
            .replace("<p>", "")
            .replace("</p>", "")
            .replace("<br/>", "")
            .replace("<br>", "")
            .replace("<b>", "")
            .replace("</b>", "")


        val locationBanner = viewholder.customViewHolder.iV_location_banner

        val bannerImageUrl = location.banner

        val defaultImage = R.drawable.no_image_found

        val feature = fromLocationId

        viewholder.customViewHolder.tV_placeDetailName.text = "Location name: " + location.name

        if (locationComments.isEmpty()) {
            viewholder.customViewHolder.tV_places_comments.text = defaultComment
        } else {
            viewholder.customViewHolder.tV_places_comments.text = locationComments
        }

        viewholder.customViewHolder.tV_latitude.text = "Lat: " + location.lat.toString()
        viewholder.customViewHolder.tV_longitude.text = "Lon: " + location.lon.toString()

        if (bannerImageUrl.isEmpty()) {
            Picasso.get().load(defaultImage).into(locationBanner)
        } else {

            Picasso.get().load(bannerImageUrl).into(locationBanner)
        }

        val locationName = fromLocationId.place.name
        val locationLon = fromLocationId.place.lon
        val locationLat = fromLocationId.place.lat

        viewholder.customViewHolder.iV_ic_google_marker.setOnClickListener {

            ToMap(viewholder.customViewHolder, locationName, locationLon, locationLat)
        }

        viewholder.fromLocationId = feature
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationDetailsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val customView = layoutInflater.inflate(R.layout.location_details_row, parent, false)

        return LocationDetailsViewHolder(
            customView,
            fromLocationId
        )
    }

    
    
    
    private fun ToMap(view: View, name: String, lon: Double, lat: Double) {

        val LOCATION_NAME_KEY = "LOCATION_NAME"
        val LOCATION_LON_KEY = "LOCATION_LON"
        val LOCATION_LAT_KEY = "LOCATION_LAT"

        val intent_Map = Intent(view.context, LocationMapsActivity::class.java)
        intent_Map.putExtra(LOCATION_NAME_KEY, name)
        intent_Map.putExtra(LOCATION_LON_KEY, lon)
        intent_Map.putExtra(LOCATION_LAT_KEY, lat)
        view.context.startActivity(intent_Map)
    }

}