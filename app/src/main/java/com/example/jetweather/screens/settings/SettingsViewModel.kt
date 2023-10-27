package com.example.jetweather.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetweather.model.Favorite
import com.example.jetweather.model.Unit
import com.example.jetweather.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository): ViewModel() {
    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged()
                .collect{ listOfUnits ->
                    if (listOfUnits.isNotEmpty()) {
                        _unitList.value = listOfUnits
                    }
                }
        }
    }

    fun insertUnit(unit: Unit) = viewModelScope.launch {
        repository.insertUnit(unit)
    }
    fun deleteAllUnit() = viewModelScope.launch {
        repository.deleteAllUnit()
    }

}