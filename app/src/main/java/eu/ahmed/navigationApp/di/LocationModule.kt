package eu.ahmed.navigationApp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.ahmed.navigationApp.data.location.DefaultLocationTracker
import eu.ahmed.navigationApp.domain.abstraction.location.LocationTracker
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationService(locationTracker: DefaultLocationTracker):LocationTracker
}