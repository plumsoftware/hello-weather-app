package ru.plumsoftware.helloweather.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.plumsoftware.helloweather.*
import ru.plumsoftware.helloweather.activities.SettingsActivityUnits
import ru.plumsoftware.helloweather.basedata.BaseNames
import ru.plumsoftware.helloweather.items.SingleSettingsItem

class SingleAdapter(_context: Context, _activity: Activity, _items: List<SingleSettingsItem>) :
    RecyclerView.Adapter<ViewHolder>() {
    private val context = _context
    private val activity = _activity
    private val items = _items

    private val editor: SharedPreferences.Editor = sharedPreferences?.edit()!!

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.single_settings_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        when (position) {
            0 -> {
                holder.textViewAreaName.text = "Выберите значение"
            }
            else -> {
                holder.textViewAreaName.visibility = View.GONE
            }
        }

        Glide.with(context).load(items[position].icon).into(holder.settingsLogo)
        holder.textViewName.text = items[position].name

//        Clickers
        holder.l1.setOnClickListener {
            if (items[position].settingName != BaseNames.SETTINGS_LIST_VALUE[4]) {
                editor.putFloat(items[position].settingName, items[position].settingsValue!!)
                editor.putString(items[position].settingName2, items[position].settingsValue2!!)

                editor.apply()
                activity.finish()
                activity.overridePendingTransition(0, 0)
                activity.startActivity(Intent(context, SettingsActivityUnits()::class.java))
            } else {
                editor.putInt(items[position].settingName, items[position].settingsValue!!.toInt())
                editor.putString(items[position].settingName2, items[position].settingsValue2!!)

                editor.apply()
                activity.finish()
                activity.overridePendingTransition(0, 0)
                activity.startActivity(Intent(context, MainActivity()::class.java))
            }
        }
    }
}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var settingsLogo = itemView.findViewById<ImageView>(R.id.settingsLogo)

    var textViewName = itemView.findViewById<TextView>(R.id.textViewName)
    var textViewValue = itemView.findViewById<TextView>(R.id.textViewValue)
    var textViewAreaName = itemView.findViewById<TextView>(R.id.textViewAreaName)

    var l1 = itemView.findViewById<LinearLayout>(R.id.l1)
}