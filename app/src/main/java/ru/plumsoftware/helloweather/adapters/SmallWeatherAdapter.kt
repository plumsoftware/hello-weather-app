package ru.plumsoftware.helloweather.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.plumsoftware.helloweather.R
import ru.plumsoftware.helloweather.basedata.BaseNames
import ru.plumsoftware.helloweather.items.SingleSettingsItem
import ru.plumsoftware.helloweather.items.SmallWeatherItem

class SmallWeatherAdapter(_context: Context, _activity: Activity, _items: List<SmallWeatherItem>) :
    RecyclerView.Adapter<ViewHolderSW>() {

    private val context = _context
    private val activity = _activity
    private val items = _items

    private var height: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderSW {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.temp_item, parent, false)
        return ViewHolderSW(itemView)
    }

    override fun getItemCount() = items.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolderSW, position: Int) {
        val item = items[position]

//        Text
        Glide.with(context).load(item.icon).into(holder.imageViewSmallLogo)
        holder.textViewTemp.text = item.temp.toString() + "°"
        holder.textViewTime.text = item.time
        holder.textViewWind.text = item.dir

//        Attrs
        height = (220 + kotlin.math.abs(item.temp) * BaseNames.HEIGHT_COEFFICIENT).toInt()
        val layoutParams = holder.l1.layoutParams
        layoutParams.height = height
        holder.l1.layoutParams = layoutParams

//        Images
        holder.imageView13.rotation = (((item.deg + 22) / 45) * 45).toFloat()
    }
}

class ViewHolderSW(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var textViewTemp = itemView.findViewById<TextView>(R.id.textViewTemp)
    var textViewWind = itemView.findViewById<TextView>(R.id.textViewWind)
    var textViewTime = itemView.findViewById<TextView>(R.id.textViewTime)

    var imageViewSmallLogo = itemView.findViewById<ImageView>(R.id.imageViewSmallLogo)
    var imageView13 = itemView.findViewById<ImageView>(R.id.imageView13)

    var l1 = itemView.findViewById<LinearLayout>(R.id.l1)
    var l = itemView.findViewById<LinearLayout>(R.id.l)
}