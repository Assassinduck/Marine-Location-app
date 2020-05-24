package com.example.android_boat_exam_final.data

data class Locations(

    val features: List<Features>
)

data class Features(

    val type: String,
    val properties: Properties,
    val geometry: Geometry
)

data class Geometry(

    val type: String,
    val coordinates: Array<Double>
)


data class Properties(

    val id: Long,
    val name: String,
    val icon: String
)

data class FromLocationId(
    val place: Place
)

data class Place(
    val id: Long,
    val name: String,
    val comments: String,
    val banner: String,
    val lat: Double,
    val lon: Double
)

