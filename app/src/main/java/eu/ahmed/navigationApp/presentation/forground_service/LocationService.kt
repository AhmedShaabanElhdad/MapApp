package eu.ahmed.navigationApp.presentation.forground_service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import eu.ahmed.navigationApp.domain.abstraction.location.LocationTracker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class LocationService: Service() {

    @Inject
    lateinit var locationTracker: LocationTracker

    val scope  = CoroutineScope(SupervisorJob() + Dispatchers.IO)


    override fun onBind(p0: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when(intent?.action){
            START -> start()
        }
        return super.onStartCommand(intent, flags, startId)

    }

    private fun start() {

        val notificationBuilder = NotificationCompat.Builder(this,"location")
            .setContentTitle("Location")
            .setContentText("current Location ")
            .setSmallIcon(android.R.drawable.ic_notification_overlay)
            .setOngoing(true)


        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        scope.launch {
            locationTracker.getLocationsUpdate(1000).catch {  }
                .collect{ location ->
                    location?.let { myLocation ->
                        notificationManager.notify(1,notificationBuilder.setContentText("your location is ${myLocation.latitude}:${myLocation.longitude} ").build())
                    }
            }
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "location",
                "Location",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
        startForeground(1,notificationBuilder.build())

    }

    override fun onDestroy() {
        super.onDestroy()
//        stopSelf()
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            stopForeground(STOP_FOREGROUND_DETACH)
//        }
    }

    companion object{
        const val START = "StartTracking"
    }
}