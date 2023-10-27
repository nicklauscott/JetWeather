package com.example.jetweather.repository

import com.example.jetweather.data.DataOrException
import com.example.jetweather.model.Weather
import com.example.jetweather.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {
    suspend fun getWeather(cityQuery: String, units: String): DataOrException<Weather, Boolean, Exception>{
        val response = try {
            api.getWeather(query = cityQuery, units = units)
        }catch (ex: Exception) {
            return DataOrException(exception = ex)
        }
        return DataOrException(data = response)
    }
}