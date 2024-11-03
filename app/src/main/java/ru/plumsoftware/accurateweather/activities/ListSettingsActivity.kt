package ru.plumsoftware.accurateweather.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.plumsoftware.accurateweather.MainActivity
import ru.plumsoftware.accurateweather.R
import ru.plumsoftware.accurateweather.adapters.SingleAdapter
import ru.plumsoftware.accurateweather.basedata.BaseNames
import ru.plumsoftware.accurateweather.basedata.BaseUnits
import ru.plumsoftware.accurateweather.items.SingleSettingsItem

class ListSettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_units)

//        Variables
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val settingsRecyclerView = findViewById<RecyclerView>(R.id.settingsRecyclerView)
        val list: ArrayList<SingleSettingsItem> = ArrayList()

        MainActivity.loadSettings()

        when (intent.extras?.getInt("mode", 0)) {
            3 -> {
//                Set base data
                toolbar.title = "Скорость ветра"
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)

//                Loop
                for (i in 0 until BaseUnits.wind_speed.size) {
                    list.add(
                        SingleSettingsItem(
                            _name = BaseUnits.wind_speed[i],
                            _icon = BaseUnits.wind_speed_icons[i],

                            _settingsName = BaseNames.SETTINGS_LIST_VALUE[2],
                            _settingsValue = BaseUnits.wind_speed_value[i],

                            _settingsName2 = BaseNames.SETTINGS_LIST[2],
                            _settingsValue2 = BaseUnits.wind_speed[i]
                        )
                    )
                }
            }
            4 -> {
//                Set base data
                toolbar.title = "Барометрическое давление"
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)

//                Loop
                for (i in 0 until BaseUnits.barometric_pressure.size) {
                    list.add(
                        SingleSettingsItem(
                            _name = BaseUnits.barometric_pressure[i],
                            _icon = BaseUnits.barometric_pressure_icons[i],

                            _settingsName = BaseNames.SETTINGS_LIST_VALUE[3],
                            _settingsValue = BaseUnits.barometric_pressure_value[i],

                            _settingsName2 = BaseNames.SETTINGS_LIST[3],
                            _settingsValue2 = BaseUnits.barometric_pressure[i]
                        )
                    )
                }
            }
            5 -> {
//                Set base data
                toolbar.title = "Тема приложения"
                setSupportActionBar(toolbar)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
                supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)

//                Loop
                for (i in 0 until BaseUnits.appearance.size) {
                    list.add(
                        SingleSettingsItem(
                            _name = BaseUnits.appearance[i],
                            _icon = BaseUnits.appearance_icons[i],

                            _settingsName = BaseNames.SETTINGS_LIST_VALUE[4],
                            _settingsValue = BaseUnits.appearance_value[i].toFloat(),

                            _settingsName2 = BaseNames.SETTINGS_LIST[4],
                            _settingsValue2 = BaseUnits.appearance[i]
                        )
                    )
                }
            }
        }

//        Setup data
        settingsRecyclerView.setHasFixedSize(true)
        settingsRecyclerView.layoutManager = LinearLayoutManager(this)
        settingsRecyclerView.adapter = SingleAdapter(this, this, list)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
////                finish()
//                return true
//            }
//        }
        return super.onOptionsItemSelected(item)
    }
}