package com.example.jetweather.repository

import com.example.jetweather.data.WeatherDao
import com.example.jetweather.model.Favorite
import com.example.jetweather.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorite>> = weatherDao.getFavorite()
    suspend fun getFavoriteById(city: String): Favorite = weatherDao.getFavoriteById(city)
    suspend fun insertFavorite(favorite: Favorite) = weatherDao.insertFavorite(favorite)
    suspend fun deleteFavorite(favorite: Favorite) = weatherDao.deleteFavorite(favorite)
    suspend fun deleteAllFavorite() = weatherDao.deleteAllFavorite()

    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnit()
    suspend fun insertUnit(unit: Unit) = weatherDao.insertUnit(unit)
    suspend fun deleteUnit(unit: Unit) = weatherDao.deleteUnit(unit)
    suspend fun deleteAllUnit() = weatherDao.deleteAllUnits()
    suspend fun updateUnit(unit: Unit) = weatherDao.updateUnit(unit)


}