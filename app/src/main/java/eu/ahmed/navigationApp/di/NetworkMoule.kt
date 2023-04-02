package eu.ahmed.navigationApp.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import eu.ahmed.navigationApp.data.remote.CityApi
import eu.ahmed.navigationApp.data.remote.MockClientInterceptor
import eu.ahmed.navigationApp.domain.abstraction.ResourceProvider
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): CityApi = retrofit.create()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient, url: String, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideBaseURL() = "https://domain/"

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().create()


    @Provides
    @Singleton
    fun provideOkHttpClient(mockClient: MockClientInterceptor): OkHttpClient =
        OkHttpClient()
            .newBuilder()
            .addInterceptor(mockClient)
            .build()


    @Provides
    @Singleton
    fun provideMockInterceptor( resourceProvider: ResourceProvider):MockClientInterceptor = MockClientInterceptor(resourceProvider)
}