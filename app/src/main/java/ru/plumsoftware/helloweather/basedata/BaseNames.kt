package ru.plumsoftware.helloweather.basedata

object BaseNames {
    const val SETTINGS_NAME = "ru_hello_weather_settings"
    val SETTINGS_LIST = listOf(
        "units",
        "is24HourTime",
        "windSpeed",
        "barometricPressure",
        "appearance"
    )

    val SETTINGS_LIST_VALUE = listOf(
        "units_value",
        "is24HourTime_value",
        "windSpeed_value",
        "barometricPressure_value",
        "appearance_value",
        "base_city",
        "base_country",
        "base_country_code",
        "base_sublocality",
        "pattern"
    )

    const val APP_ID = "f2925a90b9dfc974adc60c4f4c28ee04"
    const val HEIGHT_COEFFICIENT = 2.0
}