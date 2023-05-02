package ru.plumsoftware.helloweather.forecastdata.hourlyforecastparts

import com.google.gson.annotations.SerializedName

data class Sys(@SerializedName("pod") var pod: String? = null)
