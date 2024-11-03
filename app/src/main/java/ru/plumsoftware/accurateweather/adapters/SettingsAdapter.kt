package ru.plumsoftware.accurateweather.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.plumsoftware.accurateweather.R
import ru.plumsoftware.accurateweather.items.SettingsItem

class SettingsAdapter(
    private val _context: Context?,
    private val _activity: Activity?,
    private val _items: List<SettingsItem>
) : RecyclerView.Adapter<SettingsViewHolder>() {

    private val context: Context? = _context
        get() {
            return field
        }
    private val activity: Activity? = _activity
        get() {
            return field
        }
    private val items: List<SettingsItem> = _items
        get() {
            return field
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.settings_item, parent, false)
        return SettingsViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
//        Setup data
        Glide.with(context!!).load(items[position].icon).into(holder.settingsLogo)
        holder.textViewName.text = items[position].name
        holder.textViewValue.text = items[position].value

        holder.textViewSubTitle.visibility = View.GONE
        holder.switch1.visibility = View.GONE

        when (position) {
            0 -> {
                holder.textViewAreaName.visibility = View.VISIBLE
                holder.textViewAreaName.text = "Настройки отображения"
            }
            2 -> {
                holder.textViewAreaName.visibility = View.VISIBLE
                holder.textViewAreaName.text = "Есть вопросы или нужна помощь"
            }
            5 -> {
                holder.textViewAreaName.visibility = View.VISIBLE
                holder.textViewAreaName.text = "Ещё немного интересного"
            }
            else -> {
                holder.textViewAreaName.visibility = View.GONE
            }
        }

//        Clickers
        holder.l1.setOnClickListener {
            activity?.startActivity(
                Intent(
                    context,
                    items[position].activity::class.java
                ).putExtra("mode", 5)
            )
        }
    }
}

class SettingsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var settingsLogo = itemView.findViewById<ImageView>(R.id.settingsLogo)

    var textViewName = itemView.findViewById<TextView>(R.id.textViewName)
    var textViewSubTitle = itemView.findViewById<TextView>(R.id.textViewSubTitle)
    var textViewValue = itemView.findViewById<TextView>(R.id.textViewValue)
    var textViewAreaName = itemView.findViewById<TextView>(R.id.textViewAreaName)

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    var switch1 = itemView.findViewById<Switch>(R.id.switch1)

    var l1 = itemView.findViewById<LinearLayout>(R.id.l1)
}
