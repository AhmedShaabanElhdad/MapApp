package eu.ahmed.navigationApp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DispaterModule {

    @Provides
    @Singleton
    @IODispatcher
    fun provideIoDisptcher() = Dispatchers.IO


}


@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IODispatcher


