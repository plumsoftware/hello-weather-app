package ru.plumsoftware.helloweather.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.plumsoftware.helloweather.activities.ListSettingsActivity
import ru.plumsoftware.helloweather.R
import ru.plumsoftware.helloweather.activities.SettingsActivityUnits
import ru.plumsoftware.helloweather.basedata.BaseNames
import ru.plumsoftware.helloweather.basedata.BaseUnits
import ru.plumsoftware.helloweather.basedata.BaseUnits._12hours_pattern
import ru.plumsoftware.helloweather.basedata.BaseUnits.base_pattern
import ru.plumsoftware.helloweather.items.UnitItem
import ru.plumsoftware.helloweather.sharedPreferences

class SettingsUnitsAdapter(
    private val _context: Context?,
    private val _activity: Activity?,
    private val _items: List<UnitItem>
) : RecyclerView.Adapter<UnitsViewHolder>() {

    private val context: Context? = _context
        get() {
            return field
        }
    private val activity: Activity? = _activity
        get() {
            return field
        }
    private val items: List<UnitItem> = _items
        get() {
            return field
        }
    val editor: SharedPreferences.Editor = sharedPreferences?.edit()!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UnitsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.settings_item, parent, false)
        return UnitsViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: UnitsViewHolder, position: Int) {
//        Setup data
        Glide.with(context!!).load(items[position].icon).into(holder.settingsLogo)
        holder.textViewName.text = items[position].name
        holder.textViewSubTitle.text = items[position].subtitle
        holder.textViewValue.text = items[position].value

        holder.switch1.visibility = View.GONE
        if (position == 2) {
            holder.switch1.visibility = View.VISIBLE
            holder.switch1.isChecked = items[position].value.toBoolean()
            holder.textViewValue.visibility = View.GONE
        }

        when (position) {
            0 -> {
                holder.textViewAreaName.visibility = View.VISIBLE
                holder.textViewAreaName.text = "Источник данных"
            }
            2 -> {
                holder.textViewAreaName.visibility = View.VISIBLE
                holder.textViewAreaName.text = "пользовательские единицы"
            }
            6 -> {
                holder.textViewAreaName.visibility = View.VISIBLE
                holder.textViewAreaName.text = "Специальные режимы"
            }
            else -> {
                holder.textViewAreaName.visibility = View.GONE
            }
        }

//        Clickers
        holder.switch1.setOnCheckedChangeListener { _, b ->
            editor.putBoolean(BaseNames.SETTINGS_LIST_VALUE[1], b)
            if (b)
                editor.putString(BaseNames.SETTINGS_LIST_VALUE[9], base_pattern)
            else
                editor.putString(BaseNames.SETTINGS_LIST_VALUE[9], _12hours_pattern)
            editor.apply()
        }

        if (position < 2)
            holder.l1.setOnClickListener {
                when (position) {
                    0 -> {
                        editor.putString(BaseNames.SETTINGS_LIST[0], BaseUnits.units[0])
                        editor.putString(BaseNames.SETTINGS_LIST_VALUE[0], BaseUnits.units_value[0])
                    }
                    1 -> {
                        editor.putString(BaseNames.SETTINGS_LIST[0], BaseUnits.units[1])
                        editor.putString(BaseNames.SETTINGS_LIST_VALUE[0], BaseUnits.units_value[1])
                    }
                }
                editor.apply()
                activity?.finish()
                activity?.overridePendingTransition(0, 0)
                activity?.startActivity(Intent(context, SettingsActivityUnits::class.java))
            }
        else if (position > 2) {
            holder.l1.setOnClickListener {
                activity?.finish()
                activity?.overridePendingTransition(0, 0)
                activity?.startActivity(
                    Intent(
                        context,
                        ListSettingsActivity::class.java
                    ).putExtra("mode", position)
                )
            }
        }
    }
}

class UnitsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var settingsLogo = itemView.findViewById<ImageView>(R.id.settingsLogo)

    var textViewName = itemView.findViewById<TextView>(R.id.textViewName)
    var textViewSubTitle = itemView.findViewById<TextView>(R.id.textViewSubTitle)
    var textViewValue = itemView.findViewById<TextView>(R.id.textViewValue)
    var textViewAreaName = itemView.findViewById<TextView>(R.id.textViewAreaName)

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    var switch1 = itemView.findViewById<Switch>(R.id.switch1)

    var l1 = itemView.findViewById<LinearLayout>(R.id.l1)
}