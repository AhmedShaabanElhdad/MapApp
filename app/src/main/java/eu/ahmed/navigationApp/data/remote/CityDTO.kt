package eu.ahmed.navigationApp.data.remote

import com.google.android.gms.maps.model.LatLng
import eu.ahmed.navigationApp.domain.entity.City


data class CityDTO(
    val id: Int,
    val lat: Double,
    val lng: Double,
    val name: String
)


fun List<CityDTO>.toModel():List<City> = map {
    City(
        id = it.id,
        latLong = LatLng(it.lat,it.lng),
        name = it.name
    )
}