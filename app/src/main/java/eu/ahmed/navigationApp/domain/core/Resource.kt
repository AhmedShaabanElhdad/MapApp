package eu.ahmed.navigationApp.domain.core

sealed class Resource<out T> {
    object Loading:Resource<Nothing>()
    data class Error(val message:String):Resource<Nothing>()
    data class Data<T>(val data:T):Resource<T>()
}