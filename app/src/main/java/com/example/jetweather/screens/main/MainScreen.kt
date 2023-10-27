package com.example.jetweather.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.jetweather.R
import com.example.jetweather.data.DataOrException
import com.example.jetweather.model.Weather
import com.example.jetweather.model.WeatherItem
import com.example.jetweather.navigation.WeatherScreens
import com.example.jetweather.screens.settings.SettingsViewModel
import com.example.jetweather.utils.formatDate
import com.example.jetweather.utils.formatDecimals
import com.example.jetweather.utils.formatToDayOfWeek
import com.example.jetweather.utils.formatToHour
import com.example.jetweather.widgets.WeatherAppTopBar

@Composable
fun MainScreen(cityName: String = "Berlin", navController: NavController,
               settingsViewModel: SettingsViewModel = hiltViewModel(),
               viewModel: MainViewModel = hiltViewModel()) {
    val curCity: String = cityName.ifBlank { "London" }
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    val unit = remember {
        mutableStateOf("metric")
    }
    val isMetric = remember {
        mutableStateOf(false)
    }

    if (unitFromDb.isNotEmpty()) {
        unit.value = unitFromDb[0].unit.split(" ")[0].lowercase()
        isMetric.value = unit.value == "metric"

        val weatherData = produceState<DataOrException<Weather, Boolean, Exception>>(
            initialValue = DataOrException(isLoading = true)) {
            value = viewModel.getWeatherData(city = curCity,
                units = unit.value)
        }.value

        if (weatherData.isLoading == true) {
            CircularProgressIndicator()
        }else if (weatherData.data != null) {
            MainScaffold(weather = weatherData.data!!, navController, isMetric = isMetric)
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(weather: Weather, navController: NavController, isMetric: MutableState<Boolean>) {
    Scaffold(
        topBar = {
            WeatherAppTopBar(title = weather.city.name + ",  " + weather.city.country,
                navController = navController, elevation = 5.dp,
                onAddActionClicked = {
                      navController.navigate(WeatherScreens.SearchScreen.name)
                })
        }
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            MainContent(weather, isMetric = isMetric)
        }
    }
}

@Composable
fun MainContent(data: Weather, isMetric: MutableState<Boolean>) {
    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}@2x.png"
    Column(modifier = Modifier
        .padding(4.dp)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = formatDate(weatherItem.dt),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(6.dp))

        Surface(modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally) {
                WeatherStateImage(imageUrl = imageUrl)
                Text(text = formatDecimals(weatherItem.main.temp) + "°",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground)

                Text(text = weatherItem.weather[0].description,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground)
            }
        }
        WindPressureRow(weatherItem = weatherItem, isMetric = isMetric)
        Divider()
        WeekForecast(data = data)

    }
}

@Composable
fun WeekForecast(data: Weather) {
    Column(modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Today",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.padding(6.dp))
        Spacer(modifier = Modifier.height(5.dp))
        val days = mutableListOf<String>()
        Surface(modifier = Modifier.fillMaxSize(),
            color = Color.LightGray.copy(0.3f),
            shape = RoundedCornerShape(14.dp)
        ) {
            LazyColumn {
                items(data.list) { item ->
                    val day = formatToDayOfWeek(item.dt)
                    if (!days.contains(day)) {
                        DayCell(weatherItem = item)
                    }else {
                        days.add(day)
                    }
                }
            }
        }
    }

}

@Composable
fun DayCell(weatherItem: WeatherItem) {
    Surface(modifier = Modifier
        .fillMaxWidth()
        .padding(5.dp),
        shape = RoundedCornerShape(topStart = 45.dp, bottomStart = 45.dp,
            bottomEnd = 45.dp, topEnd = 6.dp),
        color = MaterialTheme.colorScheme.background
    ) {
       Row(modifier = Modifier.fillMaxWidth(),
           verticalAlignment = Alignment.CenterVertically,
           horizontalArrangement = Arrangement.SpaceBetween) {
           Row {
               Text(text = formatToHour(weatherItem.dt),
                   style = MaterialTheme.typography.headlineMedium,
                   color = MaterialTheme.colorScheme.onBackground,
                   modifier = Modifier.padding(6.dp))
           }
           Row {
               val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}@2x.png"
               WeatherStateImage(imageUrl = imageUrl, modifier = Modifier.size(100.dp))
           }
           Row {
               Surface(modifier = Modifier.padding(2.dp),
                   shape = RoundedCornerShape(24.dp),
                   color = Color(0xFFFFC400)
                   ) {
                   Text(text = weatherItem.weather[0].description,
                       style = MaterialTheme.typography.headlineSmall,
                       color = MaterialTheme.colorScheme.onBackground,
                       modifier = Modifier.padding(6.dp))
               }
           }
           Row {
               Text(text = formatDecimals(weatherItem.main.temp_max) + "°",
                   style = MaterialTheme.typography.headlineMedium,
                   color = Color(0xFF596CD6),
                   modifier = Modifier.padding(6.dp))
               Spacer(modifier = Modifier.width(3.dp))
               Text(text = formatDecimals(weatherItem.main.temp_min) + "°",
                   style = MaterialTheme.typography.headlineMedium,
                   color = Color.LightGray,
                   modifier = Modifier.padding(6.dp))
           }
       }
    }
}

@Composable
fun WindPressureRow(weatherItem: WeatherItem, isMetric: MutableState<Boolean>) {
    Row(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.humidity),
                contentDescription = "Humidity Icon",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(20.dp))
            Text(text = "${weatherItem.main.humidity}%",
                style = MaterialTheme.typography.headlineSmall)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.pressure),
                contentDescription = "Pressure Icon",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(20.dp))
            Text(text = "${weatherItem.main.pressure} psi",
                style = MaterialTheme.typography.headlineSmall)
        }
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(painter = painterResource(id = R.drawable.wind),
                contentDescription = "Wind Icon",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(20.dp))
            Text(text = "${weatherItem.wind.speed}" + if (isMetric.value) " m/s" else " mph",
                style = MaterialTheme.typography.headlineSmall)
        }
    }
}


@Composable
fun WeatherStateImage(imageUrl: String,
                      modifier: Modifier = Modifier) {
    AsyncImage(model = imageUrl, contentDescription = "Icon Image",
        modifier = Modifier.size(80.dp))
}
