package com.example.jetweather.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetweather.screens.about.AboutScreen
import com.example.jetweather.screens.favorite.FavoriteScreen
import com.example.jetweather.screens.main.MainScreen
import com.example.jetweather.screens.main.MainViewModel
import com.example.jetweather.screens.search.SearchScreen
import com.example.jetweather.screens.settings.SettingsScreen
import com.example.jetweather.screens.splash.WeatherSplashScreen

@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name) {
        composable(route = WeatherScreens.SplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        val route = WeatherScreens.MainScreen.name
        composable("$route/{city}",
            arguments = listOf(
                navArgument(name = "city") {
                    type = NavType.StringType
                })
        ) { navBack ->
            navBack.arguments?.getString("city").let { city ->
                val mainViewModel = hiltViewModel<MainViewModel>()
                if (city != null) {
                    MainScreen(cityName = city, navController = navController, viewModel = mainViewModel)
                }
            }

        }

        composable(route = WeatherScreens.SearchScreen.name) {
            SearchScreen(navController = navController)
        }

        composable(route = WeatherScreens.AboutScreen.name) {
            AboutScreen(navController = navController)
        }

        composable(route = WeatherScreens.FavoriteScreen.name) {
            FavoriteScreen(navController = navController)
        }

        composable(route = WeatherScreens.SettingsScreen.name) {
            SettingsScreen(navController = navController)
        }
    }
}