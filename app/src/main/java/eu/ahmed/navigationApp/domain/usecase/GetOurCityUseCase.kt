package eu.ahmed.navigationApp.domain.usecase

import eu.ahmed.navigationApp.domain.abstraction.ResourceProvider
import eu.ahmed.navigationApp.domain.abstraction.repository.PlaceRepository
import navigationApp.R
import javax.inject.Inject

class GetOurCityUseCase @Inject constructor(private val repo:PlaceRepository,private val resourceProvider: ResourceProvider){
    suspend  operator fun invoke() =  repo.getPlaces(resourceProvider.getRawResource(R.raw.cities))
}