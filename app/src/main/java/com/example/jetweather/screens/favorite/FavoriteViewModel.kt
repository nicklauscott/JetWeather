package com.example.jetweather.screens.favorite

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweather.model.Favorite
import com.example.jetweather.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(private val repository: WeatherDbRepository): ViewModel() {
    private val _favList = MutableStateFlow<List<Favorite>>(emptyList())
    val favList = _favList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFavorites().distinctUntilChanged()
                .collect{ listOfFavorites ->
                    if (listOfFavorites.isEmpty()) {
                        Log.d("FavoriteViewModelFavoriteViewModel", "Empty Favs")
                    }else {
                        _favList.value = listOfFavorites
                        Log.d("FavoriteViewModelFavoriteViewModel", "${favList.value}")
                    }
                }
        }
    }

    fun insertFavorites(favorite: Favorite) = viewModelScope.launch {
        repository.insertFavorite(favorite)
    }
    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
        repository.deleteFavorite(favorite)
    }

}