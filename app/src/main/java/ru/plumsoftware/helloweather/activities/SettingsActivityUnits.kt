package ru.plumsoftware.helloweather.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.plumsoftware.helloweather.*
import ru.plumsoftware.helloweather.adapters.SettingsUnitsAdapter
import ru.plumsoftware.helloweather.basedata.BaseUnits
import ru.plumsoftware.helloweather.items.UnitItem


class SettingsActivityUnits : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings_units)

//        Variables
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val settingsRecyclerView = findViewById<RecyclerView>(R.id.settingsRecyclerView)
        val unitItem1: UnitItem?
        val unitItem2: UnitItem?

        MainActivity.loadSettings()

        if (units == BaseUnits.units[0]) {
            unitItem1 = UnitItem(
                _name = BaseUnits.units[0],
                _subtitle = "Цельсий, метр, М/С",
                _icon = R.drawable.baseline_straighten_24,
                _value = "✓"
            )
            unitItem2 = UnitItem(
                _name = BaseUnits.units[1],
                _subtitle = "Фаренгейт, миля, М/С",
                _icon = R.drawable.globe_uk_48px,
                _value = null
            )
        } else {
            unitItem1 = UnitItem(
                _name = BaseUnits.units[0],
                _subtitle = "Цельсий, метр, М/С",
                _icon = R.drawable.baseline_straighten_24,
                _value = null
            )
            unitItem2 = UnitItem(
                _name = BaseUnits.units[1],
                _subtitle = "Фаренгейт, миля, М/С",
                _icon = R.drawable.globe_uk_48px,
                _value = "✓"
            )
        }

        val list = listOf<UnitItem>(
            unitItem1,
            unitItem2,
            UnitItem(
                _name = "24-часовой Формат времени",
                _subtitle = null,
                _icon = R.drawable.baseline_access_time_filled_24,
                _value = is24HourTime_value.toString()
            ),
            UnitItem(
                _name = "Скорость ветра",
                _subtitle = null,
                _icon = R.drawable.baseline_arrow_outward_24,
                _value = windSpeed
            ),
            UnitItem(
                _name = "Барометрическое давление",
                _subtitle = null,
                _icon = R.drawable.baseline_straighten_24,
                _value = barometricPressure
            )
        )

//        Set base data
        toolbar.title = "Единицы измерения"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)

//        Setup data
        settingsRecyclerView.setHasFixedSize(true)
        settingsRecyclerView.layoutManager = LinearLayoutManager(this)
        settingsRecyclerView.adapter = SettingsUnitsAdapter(this, this, list)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()

    }
}