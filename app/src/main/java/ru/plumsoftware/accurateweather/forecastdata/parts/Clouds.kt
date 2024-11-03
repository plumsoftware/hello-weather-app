package ru.plumsoftware.accurateweather.forecastdata.parts

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all") var all: Int? = null
)
