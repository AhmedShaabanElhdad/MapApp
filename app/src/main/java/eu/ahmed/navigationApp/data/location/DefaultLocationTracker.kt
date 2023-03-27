package eu.ahmed.navigationApp.data.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.*
import dagger.hilt.android.qualifiers.ApplicationContext
import eu.ahmed.navigationApp.domain.extension.locationPermission
import eu.ahmed.navigationApp.domain.abstraction.location.LocationTracker
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val locationManager: LocationManager,
    @ApplicationContext val context: Context
) : LocationTracker {
    @SuppressLint("MissingPermission")
    override suspend fun getCurrentLocation(): Location? {
        //permission
        val hasFineLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val isGPSEnable =
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
                LocationManager.GPS_PROVIDER
            )

        if (!hasCoarseLocation || !hasFineLocation || !isGPSEnable) {
            return null
        }
        // get location
        return suspendCancellableCoroutine { coroutine ->

            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful)
                        coroutine.resume(result)
                    else
                        coroutine.resume(null)
                    return@suspendCancellableCoroutine
                }

                addOnSuccessListener {
                    coroutine.resume(it)
                }
                addOnFailureListener {
                    coroutine.resume(null)
                }
                addOnCanceledListener {
                    coroutine.cancel()
                }
            }

        }
    }


    @SuppressLint("MissingPermission")
    override suspend fun getLocationsUpdate(_interval:Long):Flow<Location?> {
        return callbackFlow {
            if (context.locationPermission())
                send(null)

            val isGPSEnable =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) || locationManager.isProviderEnabled(
                    LocationManager.GPS_PROVIDER
                )

            if (!isGPSEnable) {
                send(null)
            }

            val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY,_interval).build()

            val locationCallback  = object :LocationCallback(){
                override fun onLocationResult(result: LocationResult) {
                    super.onLocationResult(result)
                    result.lastLocation?.let {
                        launch {
                            send(it)
                        }

                    }

                }
            }

            locationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())

            awaitClose{
                locationClient.flushLocations()
                locationClient.removeLocationUpdates(locationCallback)
            }
        }
    }
}
