package com.omnitech.weatherapptest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.omnitech.weatherapptest.ui.theme.WeatherAppTestTheme
import com.omnitech.weatherapptest.ui.weather.WeatherScreen
import com.omnitech.weatherapptest.ui.weather.WeatherViewModel
import org.koin.android.ext.android.get
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { pad ->
                    Column (Modifier.padding(pad).fillMaxSize().background( Color(0xFFd7d9d7)), horizontalAlignment = Alignment.CenterHorizontally){
                        WeatherScreen(viewModel = koinViewModel())
                    }

                }
            }
        }
    }
}


@Preview(showBackground = true) @Composable fun GreetingPreview()
{
    WeatherAppTestTheme {
        WeatherScreen(viewModel =  koinViewModel())
    }
}