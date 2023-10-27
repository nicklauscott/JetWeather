package com.example.jetweather.network

import com.example.jetweather.model.Weather
import com.example.jetweather.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherApi {

    // https://api.openweathermap.org/data/2.5/forecast?q=london&appid=3b3d2f41895d0d5fdfc0a407e7d5f03f&units=metric
    @GET(value = "data/2.5/forecast")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "metric",
        @Query("appid") appid: String = Constants.API_KEY,
        @Query("cnt") cnt: String = "9"
    ): Weather


}