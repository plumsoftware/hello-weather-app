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
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.plumsoftware.accurateweather.MainActivity
import ru.plumsoftware.accurateweather.R
import ru.plumsoftware.accurateweather.basedata.BaseNames
import ru.plumsoftware.accurateweather.basedata.UserLocation
import ru.plumsoftware.accurateweather.database.DatabaseEntry
import ru.plumsoftware.accurateweather.sharedPreferences

class HistoryAdapter (
    _context: Context?,
    _activity: Activity?,
    _items: List<UserLocation>
) : RecyclerView.Adapter<HistoryViewHolder>() {
    private val context: Context? = _context
    private val activity: Activity? = _activity
    private val items: List<UserLocation> = _items

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.history_layout, parent, false)
        return HistoryViewHolder(itemView)
    }

    override fun getItemCount() = items.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.textViewHistoryName.text = items[position].countryName + ", " + items[position].cityName

//        Clickers
        holder.imageViewDelete.setOnClickListener {
            val databaseHelper = DatabaseEntry.DatabaseHelper(context!!)
            val database = databaseHelper.readableDatabase

            database.delete(DatabaseEntry.DatabaseEntry.TABLE_NAME, DatabaseEntry.DatabaseEntry.CITY_NAME + " = ?", arrayOf(items[position].cityName))
            database.close()

            holder.lay.visibility = View.GONE
        }
        holder.lay.setOnClickListener {
            activity?.finish()
            activity?.overridePendingTransition(0, 0)
            val intent = Intent(context, MainActivity()::class.java)
            val editor = sharedPreferences?.edit()
            editor
                ?.putString(
                    BaseNames.SETTINGS_LIST_VALUE[5],
                    items[position].cityName
                )
            editor
                ?.putString(
                    BaseNames.SETTINGS_LIST_VALUE[6],
                    items[position].countryName
                )
            editor
                ?.putString(
                    BaseNames.SETTINGS_LIST_VALUE[7],
                    items[position].countryCode
                )
            editor?.apply()
            activity?.startActivity(intent)
        }
    }
}

class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var imageViewDelete = itemView.findViewById<ImageView>(R.id.imageViewDelete)
    var textViewHistoryName = itemView.findViewById<TextView>(R.id.textViewHistoryName)
    var lay = itemView.findViewById<LinearLayout>(R.id.lay)
}
