package eu.ahmed.navigationApp.data.repository

import eu.ahmed.navigationApp.data.localFiles.CityDataSource
import eu.ahmed.navigationApp.data.remote.CityApi
import eu.ahmed.navigationApp.data.remote.toModel
import eu.ahmed.navigationApp.domain.core.Resource
import eu.ahmed.navigationApp.domain.entity.City
import eu.ahmed.navigationApp.domain.abstraction.repository.PlaceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class PlaceRepositoryImp @Inject constructor(
    private val cityApi: CityApi,
    private val dataSourceImp: CityDataSource
) : PlaceRepository {
    override suspend fun getPlaces(cityJsonString:String): Flow<Resource<List<City>>> {
        return flow {

            try {
                cityApi.getCities().takeIf { it.isSuccessful && it.code() == 200 }?.body()?.let {
                    emit(Resource.Data(it.cities.toModel()))
                    // update the file but not recommended that is why we need to use Room , realme , sqldelight , sqllite
                    // the only usage for file in testing or in first open in some situation

                } ?: kotlin.run {
                        emit(Resource.Data(dataSourceImp.getCity(cityJsonString).toModel()))
                }
            } catch (e: Exception) {
                emit(Resource.Error(message = "Network Error"))
                // get from cache
                emit(Resource.Data(dataSourceImp.getCity(cityJsonString).toModel()))
            }

        }.flowOn(Dispatchers.IO)
    }

}