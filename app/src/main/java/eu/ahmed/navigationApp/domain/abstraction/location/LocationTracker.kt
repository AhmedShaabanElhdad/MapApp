package eu.ahmed.navigationApp.domain.abstraction.location

import android.location.Location
import kotlinx.coroutines.flow.Flow

interface LocationTracker {
    suspend fun getCurrentLocation():Location?
    suspend fun getLocationsUpdate(interval:Long): Flow<Location?>
}