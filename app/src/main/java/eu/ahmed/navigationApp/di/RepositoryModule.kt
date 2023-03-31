package eu.ahmed.navigationApp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.ahmed.navigationApp.data.repository.PlaceRepositoryImp
import eu.ahmed.navigationApp.domain.abstraction.repository.PlaceRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideDataSource(placeRepository: PlaceRepositoryImp):PlaceRepository
}