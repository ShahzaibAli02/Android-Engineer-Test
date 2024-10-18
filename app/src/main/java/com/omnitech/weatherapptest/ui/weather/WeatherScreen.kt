package com.omnitech.weatherapptest.ui.weather

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator

import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import  com.omnitech.weatherapptest.common.Result
import com.omnitech.weatherapptest.common.Search
import com.omnitech.weatherapptest.data.remote.dto.WeatherResponse
import com.omnitech.weatherapptest.domain.model.ForecastDay
import com.omnitech.weatherapptest.domain.model.Hour
import com.omnitech.weatherapptest.utils.MyFonts
import com.omnitech.weatherapptest.utils.convertTo12HourFormat
import com.omnitech.weatherapptest.utils.timeDifferenceInMins
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar
import kotlin.math.roundToInt
@Composable
fun CityInputDialog(
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit
) {
    var cityName by remember { mutableStateOf("") }

    Dialog(onDismissRequest = { onDismiss() }) {
        Card(modifier = Modifier.padding(10.dp)){
            Column(modifier = Modifier.padding(10.dp)){
                Text("Enter a location", color = Color.Black)
                Spacer(modifier = Modifier.height(5.dp))
                TextField(
                        value = cityName,
                        onValueChange = { cityName = it },
                        colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black // Optional: set cursor color to black
                        ), singleLine = true
                )
                Spacer(modifier = Modifier.height(5.dp))
                ElevatedButton(modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.Black),
                        enabled = cityName.isNotBlank(),
                        onClick = {
                            onSubmit(cityName)
                        }) {
                    Text(
                            text = "Search current weather",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontFamily = MyFonts.inter(),
                            fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable fun WeatherScreen(viewModel: WeatherViewModel = koinViewModel())
{


    var showDialog by rememberSaveable { mutableStateOf(true) }
    val weatherState by viewModel.weatherState.collectAsState()
    val recentSearches by viewModel.recentSearches.collectAsState()

    if (showDialog) {
        CityInputDialog(
                onDismiss = {  },
                onSubmit = { cityName ->
                    viewModel.searchForWeather(cityName)
                    showDialog = false
                }
        )
    }

    when (weatherState)
    {
        is Result.Loading ->
        {
            CircularProgressIndicator() // Show loading
        }

        is Result.Success ->
        {
            val weather = (weatherState as Result.Success<WeatherResponse>).data
            Column {
                MainWeather(weatherResponse =weather){
                    showDialog = true
                }
                Spacer(modifier = Modifier.height(10.dp))
                RecentSearches(list = recentSearches,
                        onClickClear = {
                            viewModel.clearRecentSearches()
                        })
            }
        }

        is Result.Error ->
        {
            Column (modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "Error: ${(weatherState as Result.Error).exception.message}")
                ElevatedButton(modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.Black),
                        onClick = {
                            viewModel.searchForWeather(viewModel.searchQuery)
                        }) {
                    Text(
                            text = "Try again",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontFamily = MyFonts.inter(),
                            fontWeight = FontWeight.Medium
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                ElevatedButton(modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.elevatedButtonColors(containerColor = Color.Black),

                        onClick = {
                            showDialog = true
                        }) {
                    Text(
                            text = "Change city name",
                            color = Color.White,
                            fontSize = 13.sp,
                            fontFamily = MyFonts.inter(),
                            fontWeight = FontWeight.Medium
                    )
                }
            }

        }

        else ->
        {
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class) @Composable
fun RecentSearches(modifier: Modifier = Modifier, list: List<Search>, onClickClear: () -> Unit)
{
    val colorScheme = MaterialTheme.colorScheme
    Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .wrapContentHeight(),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
        ) {
            Text(
                    text = "Recent searches",
                    color = colorScheme.primary,
                    fontSize = 12.sp,
                    fontFamily = MyFonts.inter(),
                    fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))
            SearchesList(list)
            Spacer(modifier = Modifier.height(5.dp))
            ElevatedButton(modifier = Modifier.fillMaxWidth(),
                    onClick = onClickClear) {
                Text(
                        text = "Clear recent searches",
                        color = colorScheme.secondary,
                        fontSize = 13.sp,
                        fontFamily = MyFonts.inter(),
                        fontWeight = FontWeight.Medium
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class) @Composable fun MainWeather(
    modifier: Modifier = Modifier,
    weatherResponse: WeatherResponse,
    onClickSearch: () -> Unit
)
{
    val colorScheme = MaterialTheme.colorScheme
    Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
        ) {
            Row(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
            ) {
                Column {
                    Row {
                        Text(
                                text = weatherResponse.current!!.temp_c.toInt().toString(),
                                color = colorScheme.primary,
                                fontSize = 35.sp,
                                fontFamily = MyFonts.inter(),
                                fontWeight = FontWeight.ExtraBold
                        )
                        Text(
                                text = "°c",
                                color = colorScheme.primary,
                                fontSize = 18.sp,
                                fontFamily = MyFonts.inter(),
                                fontWeight = FontWeight.ExtraBold
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        AsyncImage(
                                model = "https:"+weatherResponse.current!!.condition.icon,
                                contentDescription = "Image loaded from URL",
                                modifier = Modifier.size(40.dp)
                        )
                    }
                    Text(text = "Current temperature")


                }

            }

            ForecastList(
                    weatherResponse.forecast!!.forecastday.getOrElse(0) {
                        ForecastDay(
                                "",
                                emptyList()
                        )
                    }.hour
            )

            ElevatedButton(modifier = Modifier.fillMaxWidth(),
                    onClick = onClickSearch) {
                Text(
                        text = "Search a new location",
                        color = colorScheme.secondary,
                        fontSize = 13.sp,
                        fontFamily = MyFonts.inter(),
                        fontWeight = FontWeight.Medium
                )
            }
        }

    }
}


@Composable fun SearchesList(list: List<Search>)
{
    val colorScheme = MaterialTheme.colorScheme
    LazyColumn() {
        items(list) { foreCastHour ->
            Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 1.dp)
            ) {
                Text(
                        text = foreCastHour.content,
                        color = Color.Black,
                        fontSize = 12.sp,
                        fontFamily = MyFonts.inter(),
                        fontWeight = FontWeight.Normal
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                        text = foreCastHour.date.timeDifferenceInMins(Calendar.getInstance().timeInMillis),
                        color = colorScheme.primary,
                        fontSize = 9.sp,
                        fontFamily = MyFonts.inter(),
                        fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Composable fun ForecastList(list: List<Hour>)
{
    LazyRow {
        items(list) { foreCastHour ->
            ForecastItem(foreCastHour)
        }
    }
}

@Composable fun ForecastItem(forecastDay: Hour)
{
    val colorScheme = MaterialTheme.colorScheme
    Card(
            modifier = Modifier.padding(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFd7d9d7))
    ) {
        Column(
                modifier = Modifier.padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row {
                Text(
                        text = forecastDay.temp_c.toInt().toString(),
                        color = colorScheme.primary,
                        fontSize = 25.sp,
                        fontFamily = MyFonts.inter(),
                        fontWeight = FontWeight.Medium
                )
                Text(
                        text = "°c",
                        color = colorScheme.primary,
                        fontSize = 15.sp,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight.ExtraBold
                )
            }
            Text(
                    text = forecastDay.time.convertTo12HourFormat(),
                    fontSize = 10.sp,
                    color = colorScheme.secondary
            )
        }
    }

}