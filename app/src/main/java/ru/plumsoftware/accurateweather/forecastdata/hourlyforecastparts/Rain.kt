package ru.plumsoftware.accurateweather.forecastdata.hourlyforecastparts

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("3h") var _3h : Double? = null
)
