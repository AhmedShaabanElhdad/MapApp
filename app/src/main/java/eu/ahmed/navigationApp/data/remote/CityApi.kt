package eu.ahmed.navigationApp.data.remote

import retrofit2.http.GET
import retrofit2.Response

interface CityApi {
    @GET("getCity")
    suspend fun getCities(): Response<LocationResponse>
}