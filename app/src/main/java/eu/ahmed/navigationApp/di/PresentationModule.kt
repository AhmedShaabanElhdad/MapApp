package eu.ahmed.navigationApp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.ahmed.navigationApp.domain.abstraction.ResourceProvider
import eu.ahmed.navigationApp.presentation.core.ResourceProviderImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PresentationModule {

    @Singleton
    @Binds
    abstract fun provideResourceProvider(resourceProvider: ResourceProviderImp):ResourceProvider
}