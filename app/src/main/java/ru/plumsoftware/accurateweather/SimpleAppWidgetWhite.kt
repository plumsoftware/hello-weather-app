package ru.plumsoftware.accurateweather

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Icon
import android.widget.RemoteViews
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.plumsoftware.accurateweather.MainActivity.Companion.badIconToGoodIcon
import ru.plumsoftware.accurateweather.basedata.BaseNames
import ru.plumsoftware.accurateweather.basedata.BaseUnits
import ru.plumsoftware.accurateweather.forecastdata.CurrentWeatherForecast
import ru.plumsoftware.accurateweather.retrofitmethods.RequestsMethods
import java.util.*

/**
 * Implementation of App Widget functionality.
 */
class SimpleAppWidgetWhite : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
//    Variables
    var isNight: Boolean = false
    val calendar = Calendar.getInstance()
    isNight = calendar.get(Calendar.HOUR_OF_DAY) > 22
    val views = RemoteViews(context.packageName, R.layout.simple_app_widget_white)
    val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(BaseNames.SETTINGS_NAME, Context.MODE_PRIVATE)

//    Get data
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val create: RequestsMethods = retrofit.create(RequestsMethods::class.java)

//        Requests
    val currentWeather: Call<CurrentWeatherForecast> = create.getCurrentWeather(
        sharedPreferences.getString(BaseNames.SETTINGS_LIST_VALUE[5], "Москва")!!,
        BaseNames.APP_ID,
        sharedPreferences.getString(
            BaseNames.SETTINGS_LIST_VALUE[0],
            BaseUnits.units_value[0]
        )!!,
        "ru"
    )

//    Set data
    views.setTextViewText(
        R.id.textViewCity, sharedPreferences.getString(
            BaseNames.SETTINGS_LIST_VALUE[5],
            "Москва"
        )!!
    )

    /** SIZE
     * 70 * N - 30
     * **/

    currentWeather.enqueue(object : Callback<CurrentWeatherForecast> {
        override fun onResponse(
            call: Call<CurrentWeatherForecast>,
            response: Response<CurrentWeatherForecast>
        ) {
            val body: CurrentWeatherForecast? = response.body()

            views.setTextViewText(R.id.textViewStatus, body!!.weather[0].description)
            views.setTextViewText(
                R.id.textViewTempWidget,
                body.main!!.temp!!.toInt().toString() + "°"
            )
            views.setTextViewText(
                R.id.textViewFL,
                "Чувствуется как " + body.main!!.feelsLike!!.toInt().toString() + "°"
            )
            views.setTextViewText(
                R.id.textViewMax,
                body.main!!.tempMax!!.toInt().toString() + "°"
            )
            views.setTextViewText(
                R.id.textViewMin,
                body.main!!.tempMin!!.toInt().toString() + "°"
            )

            views.setImageViewIcon(
                R.id.imageViewLogoWidget,
                Icon.createWithResource(context, badIconToGoodIcon(body.weather[0].id!!, isNight))
            )
            views.setImageViewIcon(
                R.id.imageViewAr1,
                Icon.createWithResource(context, R.drawable.baseline_arrow_outward_24_widget)
            )
            views.setImageViewIcon(
                R.id.imageViewAr2,
                Icon.createWithResource(context, R.drawable.baseline_arrow_outward_24_widget)
            )
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        override fun onFailure(call: Call<CurrentWeatherForecast>, t: Throwable) {

        }

    })
    // Construct the RemoteViews object
//    views.setTextViewText(R.id.appwidget_text, widgetText)

    // Instruct the widget manager to update the widget

}