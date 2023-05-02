package ru.plumsoftware.helloweather.fragments

import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.plumsoftware.helloweather.*
import ru.plumsoftware.helloweather.activities.*
import ru.plumsoftware.helloweather.adapters.SettingsAdapter
import ru.plumsoftware.helloweather.items.SettingsItem

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        Variables
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val settingsRecyclerView = view.findViewById<RecyclerView>(R.id.settingsRecyclerView)
        MainActivity.loadSettings()

//        Set base data
        toolbar.title = "Настройки"
        val settings = listOf<SettingsItem>(
            SettingsItem(
                _name = "Единицы измерения",
                _icon = R.drawable.globe_uk_48px,
                _value = units,
                _activity = SettingsActivityUnits()
            ),
            SettingsItem(
                _name = "Тема приложения",
                _icon = R.drawable.dark_mode_48px,
                _value = appearance,
                _activity = ListSettingsActivity()
            ),
            SettingsItem(
                _name = "FAQs",
                _icon = R.drawable.help_48px,
                _value = null,
                _activity = FAQsActivity()
            ),
            SettingsItem(
                _name = "Поддержка",
                _icon = R.drawable.send_48px,
                _value = null,
                _activity = EmailSupportActivity()
            ),
            SettingsItem(
                _name = "Оценить приложение",
                _icon = R.drawable.star_48px,
                _value = null,
                _activity = RateAppActivity()
            ),

            SettingsItem(
                _name = "Что нового? " + "(" + BuildConfig.VERSION_NAME + ")",
                _icon = R.drawable.article_48px,
                _value = null,
                _activity = WhatsNewActivity()
            ),
            SettingsItem(
                _name = "Политика конфиденциальности",
                _icon = R.drawable.lock_48px,
                _value = null,
                _activity = PrivacyPolicyActivity()
            ),
            SettingsItem(
                _name = "Разработчик",
                _icon = R.drawable.person_48px,
                _value = null,
                _activity = DeveloperActivity()
            )
        )

//        Setup Recyclerview
        settingsRecyclerView.setHasFixedSize(true)
        settingsRecyclerView.layoutManager = LinearLayoutManager(context)
        settingsRecyclerView.adapter =
            SettingsAdapter(
                _context = context,
                _activity = activity,
                _items = settings
            )

        return view
    }
}