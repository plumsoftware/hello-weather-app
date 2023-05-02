package ru.plumsoftware.helloweather.forecastdata.parts

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed") var speed: Double? = null,
    @SerializedName("deg") var deg: Double? = null
)