package ru.plumsoftware.accurateweather

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.TypedValue
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.TooltipCompat
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.mobile.ads.appopenad.AppOpenAd
import com.yandex.mobile.ads.appopenad.AppOpenAdEventListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoadListener
import com.yandex.mobile.ads.appopenad.AppOpenAdLoader
import com.yandex.mobile.ads.common.AdError
import com.yandex.mobile.ads.common.AdRequestConfiguration
import com.yandex.mobile.ads.common.AdRequestError
import com.yandex.mobile.ads.common.ImpressionData
import com.yandex.mobile.ads.common.MobileAds
import ru.plumsoftware.accurateweather.basedata.BaseNames
import ru.plumsoftware.accurateweather.basedata.BaseUnits
import ru.plumsoftware.accurateweather.fragments.ForecastFragment
import ru.plumsoftware.accurateweather.fragments.LocationFragment
import ru.plumsoftware.accurateweather.fragments.SettingsFragment
import java.util.*

//    UNITS
//    DATA SOURCE
var units_value: String? = BaseUnits.units_value[0]
var units: String? = BaseUnits.units[0]

//    CUSTOM UNITS
var is24HourTime_value: Boolean? = true
var pattern: String? = BaseUnits.base_pattern

var windSpeed_value: Float = 1.0f
var windSpeed: String? = BaseUnits.wind_speed[0]

var barometricPressure_value: Float? = 1.0f
var barometricPressure: String? = BaseUnits.barometric_pressure[0]

//    APPEARANCE
var appearance_value: Int? = 0
var appearance: String? = BaseUnits.appearance[0]

var sharedPreferences: SharedPreferences? = null

var ads: Int = 0

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {
            if (ads == 0) {
                ads ++
                val appOpenAdLoader = AppOpenAdLoader(application)
                val adRequestConfigurationOpenAds =
                    AdRequestConfiguration.Builder(BaseNames.APP_OPEN_ADS).build()
                val appOpenAdLoadListener = object : AppOpenAdLoadListener {
                    override fun onAdLoaded(appOpenAd: AppOpenAd) {
                        val myAppOpenAd: AppOpenAd = appOpenAd
                        myAppOpenAd.setAdEventListener(object : AppOpenAdEventListener {
                            override fun onAdClicked() {}

                            override fun onAdDismissed() {}

                            override fun onAdFailedToShow(adError: AdError) {}

                            override fun onAdImpression(impressionData: ImpressionData?) {}

                            override fun onAdShown() {}
                        })
                        myAppOpenAd.show(this@MainActivity)
                    }

                    override fun onAdFailedToLoad(error: AdRequestError) {}
                }
                appOpenAdLoader.setAdLoadListener(appOpenAdLoadListener)
                appOpenAdLoader.loadAd(adRequestConfigurationOpenAds)
            }
        }

//        Variables
        sharedPreferences =
            this.getSharedPreferences(BaseNames.SETTINGS_NAME, Context.MODE_PRIVATE)
        loadSettings()

        var bottomBar = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        val l1 = findViewById<LinearLayout>(R.id.l1)
        val l2 = findViewById<LinearLayout>(R.id.l2)
        val l3 = findViewById<LinearLayout>(R.id.l3)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val imageView2 = findViewById<ImageView>(R.id.imageView2)
        val imageView3 = findViewById<ImageView>(R.id.imageView3)

        val textView = findViewById<TextView>(R.id.textView)
        val textView2 = findViewById<TextView>(R.id.textView2)
        val textView3 = findViewById<TextView>(R.id.textView3)

        TooltipCompat.setTooltipText(imageView2, "Прогноз погоды")

//        val userLocation = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            intent.getParcelableExtra<UserLocation>("location", UserLocation::class.java)
//        } else {
//            intent.getParcelableExtra<UserLocation>("location")
//        }
//
//        Log.i("TAGG", userLocation?.countryName.toString())

//        Theme
        if (appearance_value == 900) {
            val calendar: Calendar = Calendar.getInstance(Locale.getDefault())
            calendar.timeInMillis = System.currentTimeMillis()
            if (calendar.get(Calendar.HOUR_OF_DAY) >= 18) AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_YES
            )
            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            AppCompatDelegate.setDefaultNightMode(appearance_value!!)
        }

//        Default data
//        bottomBar.selectedItemId = R.id.forecast
        loadFragment(ForecastFragment())
        Glide.with(this).load(R.drawable.baseline_bar_chart_24_green).into(imageView2)
        textView2.setTextColor(resources.getColor(R.color.green, theme))

//        Clickers
//        bottomBar.setOnItemSelectedListener {
//            when (it.itemId){
//                R.id.forecast -> {
//                    loadFragment(ForecastFragment())
//                    true
//                }
//                R.id.settings -> {
//                    loadFragment(SettingsFragment())
//                    true
//                }
//                R.id.location -> {
//                    loadFragment(LocationFragment())
//                    true
//                }
//                else -> {false}
//            }
//        }

        l1.setOnClickListener {
            Glide.with(this).load(R.drawable.baseline_bar_chart_24).into(imageView2)
            textView2.setTextColor(getColorFromAttr(com.google.android.material.R.attr.colorOutline))

            Glide.with(this).load(R.drawable.settings_48px).into(imageView3)
            textView3.setTextColor(getColorFromAttr(com.google.android.material.R.attr.colorOutline))

            Glide.with(this).load(R.drawable.baseline_location_on_24_green).into(imageView)
            textView.setTextColor(resources.getColor(R.color.green, theme))

            loadFragment(LocationFragment())
        }
        l2.setOnClickListener {
            Glide.with(this).load(R.drawable.baseline_bar_chart_24_green).into(imageView2)
            textView2.setTextColor(resources.getColor(R.color.green, theme))

            Glide.with(this).load(R.drawable.settings_48px).into(imageView3)
            textView3.setTextColor(getColorFromAttr(com.google.android.material.R.attr.colorOutline))

            Glide.with(this).load(R.drawable.baseline_location_on_24).into(imageView)
            textView.setTextColor(getColorFromAttr(com.google.android.material.R.attr.colorOutline))

            loadFragment(ForecastFragment())
        }
        l3.setOnClickListener {
            Glide.with(this).load(R.drawable.baseline_bar_chart_24).into(imageView2)
            textView2.setTextColor(getColorFromAttr(com.google.android.material.R.attr.colorOutline))

            Glide.with(this).load(R.drawable.settings_48px_green).into(imageView3)
            textView3.setTextColor(resources.getColor(R.color.green, theme))

            Glide.with(this).load(R.drawable.baseline_location_on_24).into(imageView)
            textView.setTextColor(getColorFromAttr(com.google.android.material.R.attr.colorOutline))

            loadFragment(SettingsFragment())
        }

        imageView.setOnClickListener {
            Glide.with(this).load(R.drawable.baseline_bar_chart_24).into(imageView2)
            textView2.setTextColor(getColorFromAttr(com.google.android.material.R.attr.colorOutline))

            Glide.with(this).load(R.drawable.settings_48px).into(imageView3)
            textView3.setTextColor(getColorFromAttr(com.google.android.material.R.attr.colorOutline))

            Glide.with(this).load(R.drawable.baseline_location_on_24_green).into(imageView)
            textView.setTextColor(resources.getColor(R.color.green, theme))

            loadFragment(LocationFragment())
        }
        imageView2.setOnClickListener {
            Glide.with(this).load(R.drawable.baseline_bar_chart_24_green).into(imageView2)
            textView2.setTextColor(resources.getColor(R.color.green, theme))

            Glide.with(this).load(R.drawable.settings_48px).into(imageView3)
            textView3.setTextColor(getColorFromAttr(com.google.android.material.R.attr.colorOutline))

            Glide.with(this).load(R.drawable.baseline_location_on_24).into(imageView)
            textView.setTextColor(getColorFromAttr(com.google.android.material.R.attr.colorOutline))

            loadFragment(ForecastFragment())
        }
        imageView3.setOnClickListener {
            Glide.with(this).load(R.drawable.baseline_bar_chart_24).into(imageView2)
            textView2.setTextColor(getColorFromAttr(com.google.android.material.R.attr.colorOutline))

            Glide.with(this).load(R.drawable.settings_48px_green).into(imageView3)
            textView3.setTextColor(resources.getColor(R.color.green, theme))

            Glide.with(this).load(R.drawable.baseline_location_on_24).into(imageView)
            textView.setTextColor(getColorFromAttr(com.google.android.material.R.attr.colorOutline))

            loadFragment(SettingsFragment())
        }

        imageView.setOnLongClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imageView.tooltipText = "Местоположения"
            }
            return@setOnLongClickListener true
        }
        imageView2.setOnLongClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imageView2.tooltipText = "Прогноз погоды"
            }
            return@setOnLongClickListener true
        }
        imageView3.setOnLongClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                imageView3.tooltipText = "Настройки"
            }
            return@setOnLongClickListener true
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, fragment)
            .commit()
    }

    @ColorInt
    fun Context.getColorFromAttr(
        @AttrRes attrColor: Int,
        typedValue: TypedValue = TypedValue(),
        resolveRefs: Boolean = true
    ): Int {
        theme.resolveAttribute(attrColor, typedValue, resolveRefs)
        return typedValue.data
    }

    companion object {
        fun loadSettings() {
//        Value
            units_value = sharedPreferences?.getString(
                BaseNames.SETTINGS_LIST_VALUE[0],
                BaseUnits.units_value[0]
            )
            is24HourTime_value =
                sharedPreferences?.getBoolean(BaseNames.SETTINGS_LIST_VALUE[1], true)
            windSpeed_value = sharedPreferences!!.getFloat(
                BaseNames.SETTINGS_LIST_VALUE[2],
                BaseUnits.wind_speed_value[0]
            )
            barometricPressure_value = sharedPreferences?.getFloat(
                BaseNames.SETTINGS_LIST_VALUE[3],
                BaseUnits.barometric_pressure_value[0]
            )
            appearance_value = sharedPreferences?.getInt(
                BaseNames.SETTINGS_LIST_VALUE[4],
                BaseUnits.appearance_value[0]
            )
            pattern = sharedPreferences?.getString(
                BaseNames.SETTINGS_LIST_VALUE[9],
                BaseUnits.base_pattern
            )

//        Names
            units = sharedPreferences?.getString(BaseNames.SETTINGS_LIST[0], BaseUnits.units[0])
            windSpeed =
                sharedPreferences?.getString(BaseNames.SETTINGS_LIST[2], BaseUnits.wind_speed[0])
            barometricPressure = sharedPreferences?.getString(
                BaseNames.SETTINGS_LIST[3],
                BaseUnits.barometric_pressure[0]
            )
            appearance =
                sharedPreferences?.getString(BaseNames.SETTINGS_LIST[4], BaseUnits.appearance[0])
        }

        fun mphToBeaufort(mph: Double): Int {
            val knots = mph / 1.15 // Конвертируем мили в узлы
            return when (knots) {
                in 0.0..1.0 -> 0
                in 1.0..3.0 -> 1
                in 4.0..6.0 -> 2
                in 7.0..10.0 -> 3
                in 11.0..16.0 -> 4
                in 17.0..21.0 -> 5
                in 22.0..27.0 -> 6
                in 28.0..33.0 -> 7
                in 34.0..40.0 -> 8
                in 41.0..47.0 -> 9
                in 48.0..55.0 -> 10
                in 56.0..63.0 -> 11
                else -> 12
            }
        }

        fun metersPerSecondToBeaufort(speed: Double): Int {
            val beaufortScale = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)
            val beaufortSpeeds = listOf(
                0.0,
                0.3,
                1.6,
                3.4,
                5.5,
                8.0,
                10.8,
                13.9,
                17.2,
                20.8,
                24.5,
                28.5,
                Double.MAX_VALUE
            )
            for (i in beaufortSpeeds.indices) {
                if (speed < beaufortSpeeds[i]) {
                    return beaufortScale[i]
                }
            }
            return -1 // error case
        }

        fun convertPressure(pressure: Double): Int {
            return (pressure * barometricPressure_value!!).toInt()
        }

        fun badIconToGoodIcon(icon: Int, isNight: Boolean): Int {
            return when (icon) {
                in 200..232 -> {
                    if (isNight) BaseUnits.weather_icons[13] else BaseUnits.weather_icons[14]
                }
                in 300..321 -> {
                    BaseUnits.weather_icons[16]
                }
                in 500..531 -> {
                    if (isNight) BaseUnits.weather_icons[18] else BaseUnits.weather_icons[17]
                }
                in 600..621 -> {
                    if (isNight) BaseUnits.weather_icons[20] else BaseUnits.weather_icons[19]
                }
                in 700..721 -> {
                    if (isNight) BaseUnits.weather_icons[12] else BaseUnits.weather_icons[11]
                }
                731 -> {
                    BaseUnits.weather_icons[8]
                }
                in 741..751 -> {
                    if (isNight) BaseUnits.weather_icons[10] else BaseUnits.weather_icons[9]
                }
                in 761..771 -> {
                    BaseUnits.weather_icons[8]
                }
                781 -> {
                    BaseUnits.weather_icons[13]
                }
                800 -> {
                    if (isNight) BaseUnits.weather_icons[1] else BaseUnits.weather_icons[0]
                }
                801 -> {
                    if (isNight) BaseUnits.weather_icons[3] else BaseUnits.weather_icons[2]
                }
                802 -> {
                    if (isNight) BaseUnits.weather_icons[5] else BaseUnits.weather_icons[4]
                }
                in 803..804 -> {
                    if (isNight) BaseUnits.weather_icons[7] else BaseUnits.weather_icons[6]
                }
                else -> {
                    BaseUnits.weather_icons[0]
                }
            }
        }

        fun windDirection(deg: Int): String {
            val directions = arrayOf(
                "С", "ССВ", "СВ", "ВСВ",
                "В", "ВЮВ", "ЮВ", "ЮЮВ",
                "Ю", "ЮЮЗ", "ЮЗ", "ЗЮЗ",
                "З", "ЗСЗ", "СЗ", "ССЗ"
            )
            val index = ((deg / 22.5) + 0.5).toInt() % 16
            return directions[index]
        }

        fun windDirectionFull(deg: Int): String {
            val directions = arrayOf(
                "Севера", "Северо-северо-востока", "Северо-востока", "Восток-северо-востока",
                "Востока", "Восток-юго-востока", "Юго-востока", "Юго-юго-востока",
                "Юга", "Юго-юго-запада", "Юго-запада", "Запад-юго-запада",
                "Запада", "Запад-северо-запада", "Северо-запада", "Северо-северо-запада"
            )
            val index = ((deg / 22.5) + 0.5).toInt() % 16
            return directions[index]
        }
    }
}