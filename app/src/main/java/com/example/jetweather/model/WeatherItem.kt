package com.example.jetweather.model

data class WeatherItem(
    val dt: Int,
    val main: Main,
    val weather: List<WeatherObject>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val rain: Rain,
    val sys: Sys,
    val dt_txt: String
)