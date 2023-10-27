package com.example.jetweather.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetweather.model.Favorite
import com.example.jetweather.model.Unit

@Database(entities = [Favorite::class, Unit::class], version = 2, exportSchema = false)
abstract class WeatherDataBase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}