package eu.ahmed.navigationApp.domain.entity

import com.google.android.gms.maps.model.LatLng


data class City(
    val id: Int,
    val latLong: LatLng,
    val name: String
)