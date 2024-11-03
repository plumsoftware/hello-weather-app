package ru.plumsoftware.accurateweather.items

data class SmallWeatherItem(
    val temp: Int,
    val windSpeed: Int,
    val dir: String,
    val icon: Int,
    val time: String,
    val deg: Int
)