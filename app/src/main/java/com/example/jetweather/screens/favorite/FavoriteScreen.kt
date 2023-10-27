package com.example.jetweather.screens.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweather.model.Favorite
import com.example.jetweather.navigation.WeatherScreens
import com.example.jetweather.widgets.WeatherAppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(
    navController: NavController,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            WeatherAppTopBar(
                title = "Favorite Cities",
                icon = Icons.Default.ArrowBack,
                isMainScreen = false,
                navController = navController
            ) {
                navController.popBackStack()
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val list = viewModel.favList.collectAsState().value
                LazyColumn {
                    items(list) { city ->
                        CityRow(favoriteCity = city, navController = navController, viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun CityRow(favoriteCity: Favorite,
            navController: NavController,
            viewModel: FavoriteViewModel) {
    Surface(modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth()
        .height(50.dp)
        .clickable {
             navController.navigate(
                 WeatherScreens.MainScreen.name + "/${favoriteCity.city}"
             )},
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color(0xFFB2DFDE)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = favoriteCity.city, modifier = Modifier
                .padding(start = 4.dp))

            Surface(modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFD1E3E1)
            ) {
                Text(text = favoriteCity.country, modifier = Modifier
                    .padding(4.dp),
                    style = MaterialTheme.typography.headlineMedium)
            }
            Icon(imageVector = Icons.Rounded.Delete,
                contentDescription = "delete icon",
                modifier = Modifier.clickable {
                    viewModel.deleteFavorite(favoriteCity)
                },
                tint = Color.Red.copy(alpha = 0.3f))
        }
    }
}
