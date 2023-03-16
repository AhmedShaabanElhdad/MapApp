package eu.ahmed.navigationApp.di

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import eu.ahmed.navigationApp.data.localFiles.CityDataSource
import eu.ahmed.navigationApp.data.localFiles.CityDataSourceImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatasourceModule {

    @Singleton
    @Provides
    fun provideDataSource(@ApplicationContext context: Context,gson: Gson):CityDataSource = CityDataSourceImp(context,gson)
}