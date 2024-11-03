package ru.plumsoftware.accurateweather.basedata

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

    const val APP_ID = "dba09e6c9e97932e3e6b033d1ad8dd5c"
    const val RUSTORE_URL = "https://www.rustore.ru/catalog/app/ru.plumsoftware.helloweather"
    const val HEIGHT_COEFFICIENT = 2.0

    const val APP_OPEN_ADS = "R-M-12688737-1"
}