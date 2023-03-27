package eu.ahmed.navigationApp.domain.extension

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.annotation.RawRes
import androidx.core.content.ContextCompat
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

fun Context.locationPermission():Boolean{
    return ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_FINE_LOCATION
    )  == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED

}


fun Context.ReadFromRawFile(@RawRes name:Int) :String{
    var inputStream: InputStream = resources.openRawResource(name)
    return readTextFile(inputStream)
}

fun readTextFile(inputStream: InputStream): String {
    val outputStream = ByteArrayOutputStream()
    val buf = ByteArray(1024)
    var len: Int
    try {
        while (inputStream.read(buf).also { len = it } != -1) {
            outputStream.write(buf, 0, len)
        }
        outputStream.close()
        inputStream.close()
    } catch (e: IOException) {
    }
    return outputStream.toString()
}