package com.example.jetweather.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweather.model.Unit
import com.example.jetweather.widgets.WeatherAppTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController,
                   viewModel: SettingsViewModel = hiltViewModel()) {
    val unitToggleState = remember {
        mutableStateOf(false)
    }
    val measurements = listOf("imperial (F)", "Metric (C)")
    val choiceFromDb = viewModel.unitList.collectAsState().value

    val defaultChoice = if (choiceFromDb.isEmpty()) measurements[0]
        else choiceFromDb[0].unit

    var choiceState by remember {
        mutableStateOf(defaultChoice)
    }
    Scaffold(
        topBar = {
            WeatherAppTopBar(
                title = "Settings",
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
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = CenterHorizontally
            ) {
                Text(text = "Change Units of Measurement",
                    modifier = Modifier.padding(bottom = 15.dp))
                IconToggleButton(checked = unitToggleState.value, onCheckedChange = { toggle ->
                    unitToggleState.value = toggle
                    choiceState = if (unitToggleState.value) {
                        "Metric (C)"
                    }else {
                        "Imperial (F)"
                    }
                },
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .clip(RectangleShape)
                        .padding(5.dp)
                        .background(Color.Magenta.copy(alpha = 0.4f))) {
                    Text(text = if (unitToggleState.value) "Celsius °C" else "Fahrenheit °F")
                }
                Button(onClick = {
                    viewModel.deleteAllUnit()
                    viewModel.insertUnit(Unit(unit = choiceState))
                     },
                    modifier = Modifier
                        .padding(3.dp)
                        .align(CenterHorizontally),
                    shape = RoundedCornerShape(34.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEFBE42)
                    )
                ) {
                    Text(text = "Save", modifier = Modifier.padding(4.dp),
                        color = Color.White,
                        fontSize = 17.sp)
                }
            }
        }
    }
}