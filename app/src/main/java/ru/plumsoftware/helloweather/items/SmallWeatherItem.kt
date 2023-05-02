package ru.plumsoftware.helloweather.items

data class SmallWeatherItem(
    val temp: Int,
    val windSpeed: Int,
    val dir: String,
    val icon: Int,
    val time: String,
    val deg: Int
)