package eu.ahmed.navigationApp

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import eu.ahmed.navigationApp.domain.entity.City
import eu.ahmed.navigationApp.presentation.feature_home.CityViewModel
import eu.ahmed.navigationApp.presentation.feature_home.UIState
import eu.ahmed.navigationApp.presentation.forground_service.LocationService
import kotlinx.coroutines.*
import navigationApp.R



@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    private val viewmodel: CityViewModel by viewModels()

    private val permissionLauncher by lazy {
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            // Handle Permission granted/rejected
            permissions.entries.forEach {
                val isGranted = it.value
                if (isGranted) {
                    startMyService()
                 } else {

                 }
            }
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeMap()
        askForPermission()
        observeState()


    }

    private fun observeState() {
        lifecycleScope.launchWhenStarted {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewmodel.state.collect { state ->
                    when (state) {
                        is UIState.Error -> {}
                        UIState.IDLE -> {}
                        UIState.Loading -> {}
                        is UIState.Success -> {
                            addMarkers(state.city)
                            animateBetweenCities(state.city)
                        }
                    }
                }

            }
        }
    }

    private fun askForPermission() {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
    }

    private fun initializeMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    var serviceIntetnt: Intent? = null
    private fun startMyService() {
        serviceIntetnt = Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.START
            startService(this)
        }
    }

    private val job: Job? = null
    private fun animateBetweenCities(cities: List<City>) {
        CoroutineScope(Dispatchers.IO).launch {
            delay(3500)
            cities.forEach { city ->
                withContext(Dispatchers.Main) {
                    map.animateCamera(
                        CameraUpdateFactory.newCameraPosition(
                            CameraPosition.Builder().target(city.latLong)
                                .zoom(16f)
                                .build()
                        )
                    )
                }
                delay(3500)
            }
            cancel()
        }
    }

    private fun addMarkers(cities: List<City>) {
        cities.forEach { city ->
            map.addMarker(
                MarkerOptions()
                    .title(city.name)
                    .position(city.latLong)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
            )
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        map.uiSettings.isRotateGesturesEnabled = true
        map.uiSettings.isZoomControlsEnabled = true
        // Add a marker in Tartu and move the camera
        val tartu = LatLng(58.370850, 26.714720)
        map.addMarker(
            MarkerOptions().position(tartu).title("Tartu")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        )
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(tartu, 16f))

    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceIntetnt?.let {
            stopService(it)
        }
    }

}

