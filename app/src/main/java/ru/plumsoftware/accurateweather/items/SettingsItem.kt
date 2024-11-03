package ru.plumsoftware.accurateweather.items

import android.app.Activity

open class SettingsItem (_name : String?, _icon : Int?, _value: String?, _activity: Activity) {
    val name : String? = _name
    val icon : Int? = _icon
    val value : String? = _value
    val activity = _activity
}