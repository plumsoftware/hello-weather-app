package ru.plumsoftware.helloweather.forecastdata

import com.google.gson.annotations.SerializedName
import ru.plumsoftware.helloweather.forecastdata.hourlyforecastparts.City
import ru.plumsoftware.helloweather.forecastdata.hourlyforecastparts.List

data class HourlyWeatherForecast(
    @SerializedName("cod") var cod: String? = null,
    @SerializedName("message") var message: Int? = null,
    @SerializedName("cnt") var cnt: Int? = null,
    @SerializedName("list") var list: ArrayList<List> = arrayListOf(),
    @SerializedName("city") var city: City? = City()
)
