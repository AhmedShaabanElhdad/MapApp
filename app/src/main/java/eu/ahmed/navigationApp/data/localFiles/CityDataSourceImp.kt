package eu.ahmed.navigationApp.data.localFiles

import android.content.Context
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import eu.ahmed.navigationApp.data.remote.CityDTO
import eu.ahmed.navigationApp.data.remote.LocationResponse
import javax.inject.Inject


class CityDataSourceImp @Inject constructor(@ApplicationContext val context: Context,val gson: Gson) :CityDataSource{

    override suspend fun getCity(cityJsonString: String):List<CityDTO> {
        val locationResponse: LocationResponse = gson.fromJson(cityJsonString, LocationResponse::class.java)
        return locationResponse.cities
    }
}





