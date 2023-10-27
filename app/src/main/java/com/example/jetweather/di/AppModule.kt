package com.example.jetweather.di

import android.content.Context
import androidx.room.Room
import com.example.jetweather.data.WeatherDao
import com.example.jetweather.data.WeatherDataBase
import com.example.jetweather.network.WeatherApi
import com.example.jetweather.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideOpenWeatherApi(): WeatherApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }


    // Provides the Dao for the database
    @Provides
    @Singleton
    fun provideWeatherDao(weatherDataBase: WeatherDataBase): WeatherDao =
        weatherDataBase.weatherDao()


    // Creates the actual database with help of we get application context from
    // dagger hilt
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDataBase =
        Room.databaseBuilder(
            context,
            WeatherDataBase::class.java,
            "weather_database")
            .fallbackToDestructiveMigration()
            .build()

}