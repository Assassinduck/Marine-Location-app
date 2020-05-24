package com.example.android_boat_exam_final.ui.list


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.android_boat_exam_final.R
import com.example.android_boat_exam_final.database.PlacesEntity
import com.example.android_boat_exam_final.ui.details.LocationDetails
import com.example.android_boat_exam_final.ui.map.LocationMapsActivity
import kotlinx.android.synthetic.main.locations_individual.view.*

class LocationListAdapter(private var LocationListFull: MutableList<PlacesEntity> = mutableListOf()) : RecyclerView.Adapter<LocationListAdapter.LocationListViewHolder?>(), Filterable {

    class LocationListViewHolder(val viewHolder: View, var feature: PlacesEntity? = null) : RecyclerView.ViewHolder(viewHolder) {

        companion object {
            const val LOCATION_TITLE_KEY = "LOCATION_TITLE"
            const val LOCATION_ID_KEY = "LOCATION_ID"

        }

        init {
            viewHolder.setOnClickListener {
                println(feature?.placeName)
                println(feature?.placesId)


                val detailsIntent = Intent(viewHolder.context, LocationDetails::class.java)

                detailsIntent.putExtra(LOCATION_TITLE_KEY, feature?.placeName)
                detailsIntent.putExtra(LOCATION_ID_KEY, feature?.placesId)

                viewHolder.context.startActivity(detailsIntent)
            }
        }
    }




    private var ListToShow: MutableList<PlacesEntity> = mutableListOf()

    init {
        ListToShow = LocationListFull
    }

    override fun getItemCount(): Int {
        return ListToShow.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationListViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val cellInRow = layoutInflater.inflate(R.layout.locations_individual, parent, false)

        return LocationListViewHolder(
            cellInRow
        )
    }

    private fun goToMap(view: View, name: String, lon: Double, lat: Double) {

        val LOCATION_NAME_KEY = "LOCATION_NAME"
        val LOCATION_LON_KEY = "LOCATION_LON"
        val LOCATION_LAT_KEY = "LOCATION_LAT"

        val intent = Intent(view.context, LocationMapsActivity::class.java)
        intent.putExtra(LOCATION_NAME_KEY, name)
        intent.putExtra(LOCATION_LON_KEY, lon)
        intent.putExtra(LOCATION_LAT_KEY, lat)
        view.context.startActivity(intent)
    }

    override fun onBindViewHolder(ViewHolderLocation: LocationListViewHolder, position: Int) {

        val feature = ListToShow[position]

        ViewHolderLocation.viewHolder.tV_locations_individual_row.text = feature.placeName

        val locationName = feature.placeName
        val locationLon = feature.placeLon
        val locationLat = feature.placeLat

        ViewHolderLocation.viewHolder.iV_locations_row.setOnClickListener {
            goToMap(ViewHolderLocation.viewHolder, locationName, locationLon, locationLat)
        }

        ViewHolderLocation.feature = feature

    }



    override fun getFilter(): Filter {
        return locationFilter
    }

    private val locationFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            val filteredList: MutableList<PlacesEntity> =
                if (constraint == null || constraint.isEmpty()) {
                    LocationListFull

                } else {
                    LocationListFull.filter {
                        it.placeName.contains(
                            constraint.toString(),
                            ignoreCase = true
                        )
                    } as MutableList<PlacesEntity>

                }

            val results = FilterResults()
            results.values = filteredList
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

            results?.values.let {
                ListToShow = it as MutableList<PlacesEntity>
            }


            notifyDataSetChanged()
        }
    }


}

