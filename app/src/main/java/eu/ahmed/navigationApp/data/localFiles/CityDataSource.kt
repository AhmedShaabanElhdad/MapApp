package eu.ahmed.navigationApp.data.localFiles

import eu.ahmed.navigationApp.data.remote.CityDTO


interface CityDataSource{
    suspend fun getCity(cityJsonString:String):List<CityDTO>
}





