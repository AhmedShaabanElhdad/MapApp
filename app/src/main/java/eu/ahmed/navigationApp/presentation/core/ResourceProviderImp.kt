package eu.ahmed.navigationApp.presentation.core

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import eu.ahmed.navigationApp.domain.abstraction.ResourceProvider
import eu.ahmed.navigationApp.domain.extension.ReadFromRawFile
import javax.inject.Inject

class ResourceProviderImp @Inject constructor(@ApplicationContext val context: Context):ResourceProvider {

    override fun getRawResource(resourceId: Int):String{
        return context.ReadFromRawFile(resourceId)
    }

    override fun getResource(resourceId: Int, param: String): String {
        return context.resources.getString(resourceId,param)
    }
}