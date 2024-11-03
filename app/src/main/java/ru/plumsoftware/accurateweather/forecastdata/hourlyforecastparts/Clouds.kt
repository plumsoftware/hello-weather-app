package ru.plumsoftware.accurateweather.forecastdata.hourlyforecastparts

import com.google.gson.annotations.SerializedName

data class Clouds(@SerializedName("all") var all: Int? = null)
