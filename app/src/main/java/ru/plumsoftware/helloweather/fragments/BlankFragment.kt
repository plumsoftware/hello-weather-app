package ru.plumsoftware.helloweather.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.plumsoftware.helloweather.*
import ru.plumsoftware.helloweather.MainActivity.Companion.badIconToGoodIcon
import ru.plumsoftware.helloweather.MainActivity.Companion.metersPerSecondToBeaufort
import ru.plumsoftware.helloweather.MainActivity.Companion.mphToBeaufort
import ru.plumsoftware.helloweather.MainActivity.Companion.windDirection
import ru.plumsoftware.helloweather.adapters.SmallWeatherAdapter
import ru.plumsoftware.helloweather.basedata.BaseNames
import ru.plumsoftware.helloweather.basedata.BaseUnits
import ru.plumsoftware.helloweather.forecastdata.HourlyWeatherForecast
import ru.plumsoftware.helloweather.items.SmallWeatherItem
import ru.plumsoftware.helloweather.retrofitmethods.RequestsMethods
import java.text.SimpleDateFormat
import java.util.*

class BlankFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        Views
        val view = inflater.inflate(R.layout.fragment_blank, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        var isNight: Boolean = false
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        isNight = calendar.get(Calendar.HOUR_OF_DAY) > 22

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager =
            StaggeredGridLayoutManager(8, StaggeredGridLayoutManager.VERTICAL)
        // LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


//        Variables
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val create: RequestsMethods = retrofit.create(RequestsMethods::class.java)

//        Get data
        val hourlyWeather: Call<HourlyWeatherForecast> = create.getHourlyWeather(
            sharedPreferences?.getString(BaseNames.SETTINGS_LIST_VALUE[5], "Москва")!!,
            BaseNames.APP_ID,
            units_value!!,
            "ru"
        )
        hourlyWeather.enqueue(object : Callback<HourlyWeatherForecast> {
            override fun onResponse(
                call: Call<HourlyWeatherForecast>,
                response: Response<HourlyWeatherForecast>
            ) {
                val body: HourlyWeatherForecast? = response.body()
                val items = mutableListOf<SmallWeatherItem>()
                val start = 1 + ((arguments!!.getInt("part") + 1) - 1) * 8
                val end = start + 7
                for (i in start..end) {
                    calendar.timeInMillis = body!!.list[i].dt!! * 1000L

                    var speed: Double? = 0.0
                    speed = if (units_value == BaseUnits.units_value[0]) {
                        speed = body.list[i].wind?.speed
                        if (windSpeed_value == BaseUnits.wind_speed_value[2]) {
                            metersPerSecondToBeaufort(speed!!).toDouble()
                        } else {
                            speed?.times(windSpeed_value)
                        }
                    } else {
                        when (windSpeed) {
                            BaseUnits.wind_speed[0] -> {
                                speed?.times(0.44704)
                            }
                            BaseUnits.wind_speed[1] -> {
                                speed?.times(0.868976)
                            }
                            BaseUnits.wind_speed[2] -> {
                                mphToBeaufort(speed!!).toDouble()
                            }
                            else -> {
                                speed?.times(windSpeed_value)
                            }
                        }
                    }

                    items.add(
                        SmallWeatherItem(
                            body.list[i].main?.temp!!.toInt(),
                            speed?.toInt()!!,
                            (speed.toInt().toString() + " ")
                                .plus(windDirection(body.list[i].wind?.deg!!)),
                            badIconToGoodIcon(body.list[i].weather[0].id!!, isNight),
                            SimpleDateFormat(
                                pattern,
                                Locale.getDefault()
                            ).format(Date(calendar.timeInMillis)),
                            body.list[i].wind?.deg!!
                        )
                    )

//                    if (i == 24) {
//                        break
//                    }
                }
                try {
                    recyclerView.adapter = SmallWeatherAdapter(context!!, activity!!, items)
                } catch (e: java.lang.Exception) {

                }
            }

            override fun onFailure(call: Call<HourlyWeatherForecast>, t: Throwable) {

            }
        })


//        Adapter
//        recyclerView.setHasFixedSize(true)
//        recyclerView.layoutManager = LinearLayoutManager(context)
//        recyclerView.adapter

        return view
    }

//    private fun badIconToGoodIcon(icon: Int, isNight: Boolean): Int {
//        return when (icon) {
//            in 200..232 -> {
//                if (isNight) BaseUnits.weather_icons[13] else BaseUnits.weather_icons[14]
//            }
//            in 300..321 -> {
//                BaseUnits.weather_icons[16]
//            }
//            in 500..531 -> {
//                if (isNight) BaseUnits.weather_icons[18] else BaseUnits.weather_icons[17]
//            }
//            in 600..621 -> {
//                if (isNight) BaseUnits.weather_icons[20] else BaseUnits.weather_icons[19]
//            }
//            in 700..721 -> {
//                if (isNight) BaseUnits.weather_icons[12] else BaseUnits.weather_icons[11]
//            }
//            731 -> {
//                BaseUnits.weather_icons[8]
//            }
//            in 741..751 -> {
//                if (isNight) BaseUnits.weather_icons[10] else BaseUnits.weather_icons[9]
//            }
//            in 761..771 -> {
//                BaseUnits.weather_icons[8]
//            }
//            781 -> {
//                BaseUnits.weather_icons[13]
//            }
//            800 -> {
//                if (isNight) BaseUnits.weather_icons[1] else BaseUnits.weather_icons[0]
//            }
//            801 -> {
//                if (isNight) BaseUnits.weather_icons[3] else BaseUnits.weather_icons[2]
//            }
//            802 -> {
//                if (isNight) BaseUnits.weather_icons[5] else BaseUnits.weather_icons[4]
//            }
//            in 803..804 -> {
//                if (isNight) BaseUnits.weather_icons[7] else BaseUnits.weather_icons[6]
//            }
//            else -> {
//                BaseUnits.weather_icons[0]
//            }
//        }
//    }
}