package eu.ahmed.navigationApp.domain.abstraction

import androidx.annotation.RawRes
import androidx.annotation.StringRes


interface ResourceProvider {
    fun getRawResource(@RawRes resourceId:Int):String
    fun getResource(@StringRes resourceId:Int,param:String):String
}