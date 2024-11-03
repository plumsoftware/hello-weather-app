package ru.plumsoftware.accurateweather.items

class SingleSettingsItem(
    _name: String,
    _icon: Int,
    _settingsName: String,
    _settingsName2: String,
    _settingsValue: Float?,
    _settingsValue2: String?
) {
    val name = _name
    val icon = _icon
    val settingName = _settingsName
    val settingName2 = _settingsName2
    val settingsValue = _settingsValue
    val settingsValue2 = _settingsValue2
}