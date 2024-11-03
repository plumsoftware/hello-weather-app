package ru.plumsoftware.accurateweather.activities

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import ru.plumsoftware.accurateweather.MainActivity
import ru.plumsoftware.accurateweather.R
import ru.plumsoftware.accurateweather.basedata.BaseNames
import ru.plumsoftware.accurateweather.database.DatabaseEntry
import ru.plumsoftware.accurateweather.sharedPreferences

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

//        Views
        val searchView = findViewById<SearchView>(R.id.searchView)

//        Variables
        val databaseHelper = DatabaseEntry.DatabaseHelper(this)
        val database = databaseHelper.writableDatabase

//        Set base data
        showKeyboardAndFocusOnSearchView(searchView)

//        Clickers
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.isNotEmpty()) {
//                    for (i in 0 until loadHistory.size step 1) {
//                    if (query.lowercase().trim().replace("\\s+", "_") != loadHistory[i].cityName) {
//                    Getting country name
                    val geocoder = Geocoder(this@SearchActivity)
                    val contentValues = ContentValues()
                    val addressList = geocoder.getFromLocationName(query.trim(), 1)

                    if (addressList?.isNotEmpty()!!) {
                        val country = addressList[0].countryName

                        contentValues.put(
                            DatabaseEntry.DatabaseEntry.COUNTRY_NAME,
                            country
                        )
                    } else {
                        Toast.makeText(this@SearchActivity, "Город с таким именем не найден.", Toast.LENGTH_LONG).show()
                    }

                    contentValues.put(
                        DatabaseEntry.DatabaseEntry.CITY_NAME,
                        query.trim().replace("\\s+", "_")
                    )
                    contentValues.put(
                        DatabaseEntry.DatabaseEntry.COUNTRY_CODE,
                        ""
                    )
                    database.insert(
                        DatabaseEntry.DatabaseEntry.TABLE_NAME,
                        null,
                        contentValues
                    )
                    database.close()
//                    }
//                    }

//                     Put settings
                    val editor = sharedPreferences?.edit()
                    editor?.putString(
                        BaseNames.SETTINGS_LIST_VALUE[5],
                        query.trim().replace("\\s+", "_")
                    )
                    editor?.apply()

                    finish()
                    overridePendingTransition(0, 0)
                    startActivity(Intent(this@SearchActivity, MainActivity::class.java))
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showKeyboardAndFocusOnSearchView(searchView: SearchView) {
        searchView.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT)
    }
}