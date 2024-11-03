package ru.plumsoftware.accurateweather.retrofitmethods

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.plumsoftware.accurateweather.forecastdata.CurrentWeatherForecast
import ru.plumsoftware.accurateweather.forecastdata.HourlyWeatherForecast

interface RequestsMethods {
    @GET("weather")
    fun getCurrentWeather(
        @Query("q") q: String,
        @Query("appid") appid: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): Call<CurrentWeatherForecast>

    @GET("forecast")
    fun getHourlyWeather(
        @Query("q") q: String,
        @Query("appid") appid: String,
        @Query("units") units: String,
        @Query("lang") lang: String
    ): Call<HourlyWeatherForecast>
}