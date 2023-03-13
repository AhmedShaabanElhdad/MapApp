package eu.ahmed.navigationApp.domain.abstraction.repository

import eu.ahmed.navigationApp.domain.core.Resource
import eu.ahmed.navigationApp.domain.entity.City
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {
    suspend fun getPlaces(cityJsonString:String):Flow<Resource<List<City>>>
}