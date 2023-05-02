package ru.plumsoftware.helloweather.basedata

import androidx.appcompat.app.AppCompatDelegate
import ru.plumsoftware.helloweather.R

object BaseUnits {
    val units = listOf(
        "Метрическая",
        "Империческая"
    )
    val units_value = listOf(
        "metric",
        "imperial"
    )

    val base_pattern = "HH:mm"
    val _12hours_pattern = "hh:mm"

    val wind_speed = listOf(
        "М/С",
        "Морские узлы",
        "Бофорт"
    )
    val wind_speed_value = listOf(
        1.0f,
        1.94384f,
        -1.0f
    )
    val wind_speed_icons = listOf(
        R.drawable.baseline_arrow_outward_24,
        R.drawable.baseline_arrow_outward_24,
        R.drawable.baseline_arrow_outward_24
    )

    val barometric_pressure = listOf(
        "миллибары (мб)",
        "Миллиметры ртутного столба (мм. рт. ст.)",
        "Дюймы ртутного столба",
        "Гектопаскали (гПа)",
        "Килопаскали (кПа)"
    )
    val barometric_pressure_icons = listOf(
        R.drawable.device_thermostat_48px,
        R.drawable.device_thermostat_48px,
        R.drawable.device_thermostat_48px,
        R.drawable.device_thermostat_48px,
        R.drawable.device_thermostat_48px,
        R.drawable.device_thermostat_48px
    )
    val barometric_pressure_value = listOf(
        1.0f,
        0.750062f,
        0.029529983f,
        0.01f,
        1.0f
    )

    val appearance = listOf(
        "Системная",
        "Динамическая",
        "Светлая",
        "Тёмная"
    )
    val appearance_value = listOf<Int>(
        AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
        900,
        AppCompatDelegate.MODE_NIGHT_NO,
        AppCompatDelegate.MODE_NIGHT_YES
    )

    val appearance_icons = listOf(
        R.drawable.settings_48px_fill,
        R.drawable.baseline_location_on_24_blue,
        R.drawable.baseline_light_mode_24,
        R.drawable.baseline_dark_mode_24,
    )

    val weather_icons = listOf(
        R.drawable.clear_day,
        R.drawable.dark_mode_48px,
        R.drawable.cloudy_1_day,
        R.drawable.cloudy_1_night,
        R.drawable.cloudy_2_day,
        R.drawable.cloudy_2_night,
        R.drawable.cloudy_3_day,
        R.drawable.cloudy_3_night,
        R.drawable.dust,
        R.drawable.fog_day,
        R.drawable.fog_night,
        R.drawable.haze_day,
        R.drawable.haze_night,
        R.drawable.hurricane,
        R.drawable.isolated_thunderstorms_day,
        R.drawable.isolated_thunderstorms_night,
        R.drawable.rainy_3,
        R.drawable.rainy_3_day,
        R.drawable.rainy_3_night,
        R.drawable.snowy_3_day,
        R.drawable.snowy_3_night
    )
}