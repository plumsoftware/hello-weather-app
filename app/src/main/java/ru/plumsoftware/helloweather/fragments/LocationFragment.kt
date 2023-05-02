package ru.plumsoftware.helloweather.fragments

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.location.LocationRequest
import android.os.Bundle
import android.provider.BaseColumns
import android.view.*
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.snackbar.Snackbar
import ru.plumsoftware.helloweather.MainActivity
import ru.plumsoftware.helloweather.R
import ru.plumsoftware.helloweather.activities.SearchActivity
import ru.plumsoftware.helloweather.adapters.HistoryAdapter
import ru.plumsoftware.helloweather.basedata.BaseNames
import ru.plumsoftware.helloweather.basedata.UserLocation
import ru.plumsoftware.helloweather.database.DatabaseEntry
import ru.plumsoftware.helloweather.sharedPreferences
import java.util.*
import kotlin.collections.ArrayList

class LocationFragment : Fragment() {
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
//    private lateinit var userLocation: UserLocation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        Variables
        val view = inflater.inflate(R.layout.fragment_location, container, false)
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)
        val curLoc = view.findViewById<LinearLayout>(R.id.locL)
        val recyclerViewLocations = view.findViewById<RecyclerView>(R.id.recyclerViewLocations)
        val history: java.util.ArrayList<UserLocation> = loadHistory(requireContext())

//        Set base data
        toolbar.title = "Местоположения"

//        Clickers
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }

        curLoc.setOnClickListener {
            if (isLocationEnabled()) {
                fetchLocation()
            } else {
                Snackbar.make(
                    requireContext(),
                    view.findViewById(R.id.linearLayout),
                    "Определение местоположения недоступно на вашем устройстве",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

//        Setup recyclerview
        recyclerViewLocations.setHasFixedSize(true)
        recyclerViewLocations.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewLocations.adapter =
            HistoryAdapter(requireContext(), requireActivity(), history)
        return view
    }

    private fun getCity(lat: Double, long: Double): String? {

        val geoCoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())
        val address = geoCoder.getFromLocation(lat, long, 1)

        return address?.get(0)?.locality
    }

    private fun getSubLocality(lat: Double, long: Double): String? {
        val geoCoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())
        val address = geoCoder.getFromLocation(lat, long, 1)

        return address?.get(0)?.subLocality
    }

    private fun getCountry(lat: Double, long: Double): String? {

        val geoCoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())
        val address = geoCoder.getFromLocation(lat, long, 1)

        return address?.get(0)?.countryName
    }

    private fun getCountryCode(lat: Double, long: Double): String? {

        val geoCoder: Geocoder = Geocoder(requireContext(), Locale.getDefault())
        val address = geoCoder.getFromLocation(lat, long, 1)

        return address?.get(0)?.countryCode
    }

    private fun fetchLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                101
            )
            return
        } else {
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            val task = fusedLocationProviderClient.lastLocation

            task.addOnSuccessListener {
                if (it != null) {
                    val databaseHelper = DatabaseEntry.DatabaseHelper(requireContext())
                    val database = databaseHelper.writableDatabase
                    val contentValues = ContentValues()
                    contentValues.put(
                        DatabaseEntry.DatabaseEntry.CITY_NAME,
                        getCity(it.latitude, it.longitude)?.replace("\\s+", "_")
                    )
                    contentValues.put(
                        DatabaseEntry.DatabaseEntry.COUNTRY_NAME,
                        getCountry(it.latitude, it.longitude)
                    )
                    contentValues.put(
                        DatabaseEntry.DatabaseEntry.COUNTRY_CODE,
                        getCountryCode(it.latitude, it.longitude)
                    )
                    database.insert(
                        DatabaseEntry.DatabaseEntry.TABLE_NAME,
                        null,
                        contentValues
                    )
                    database.close()
//                    userLocation = UserLocation(
//                        it.latitude,
//                        it.longitude,
//                        getCity(it.latitude, it.longitude),
//                        getCountry(it.latitude, it.longitude),
//                        getCountryCode(it.latitude, it.longitude)
//                    )

                    activity?.finish()
                    activity?.overridePendingTransition(0, 0)
                    val intent = Intent(requireContext(), MainActivity()::class.java)
                    val editor = sharedPreferences?.edit()
                    editor
                        ?.putString(
                            BaseNames.SETTINGS_LIST_VALUE[5],
                            getCity(it.latitude, it.longitude)
                        )
                    editor
                        ?.putString(
                            BaseNames.SETTINGS_LIST_VALUE[6],
                            getCountry(it.latitude, it.longitude)
                        )
                    editor
                        ?.putString(
                            BaseNames.SETTINGS_LIST_VALUE[7],
                            getCountryCode(it.latitude, it.longitude)
                        )
                    editor
                        ?.putString(
                            BaseNames.SETTINGS_LIST_VALUE[8],
                            getSubLocality(it.latitude, it.longitude)
                        )
                    editor?.apply()
//                    Не сработал parcelable, не понял почему
                    //intent.putExtra("location", userLocation)
                    activity?.startActivity(intent)
                }
            }
//            fusedLocationProviderClient.lastLocation.addOnCompleteListener() { task ->
//                val location: Location? = task.result
//
//                if (location == null){
//
//                } else {
//
//                }
//            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager: LocationManager =
            requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_a -> {
                activity?.startActivity(Intent(requireContext(), SearchActivity()::class.java))
                activity?.overridePendingTransition(0, 0)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 101) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fetchLocation()
            }
        }
    }

    @SuppressLint("Recycle")
    fun loadHistory(context: Context): ArrayList<UserLocation> {
        val databaseHelper = DatabaseEntry.DatabaseHelper(context)
        val database = databaseHelper.readableDatabase

//        val values = ContentValues()
//        values.put(EventDatabaseEntry.EventDatabaseConstants.COLUMN_NAME_EVENT, name)
//        val newRowId =
//            database?.insert(EventDatabaseEntry.EventDatabaseConstants.TABLE_NAME, null, values)

//        val projection = arrayOf(
//            BaseColumns._ID,
//            DatabaseEntry.DatabaseEntry.CITY_NAME,
//            DatabaseEntry.DatabaseEntry.COUNTRY_NAME,
//            DatabaseEntry.DatabaseEntry.COUNTRY_CODE
//        )
//
//        val selection = "${DatabaseEntry.DatabaseEntry.COUNTRY_NAME} = ?"
//        val selectionArgs = arrayOf("Title")
//
        val sortOrder = "${BaseColumns._ID} ASC"


        val cursor = database.query(
            DatabaseEntry.DatabaseEntry.TABLE_NAME,   // The table to query
            null,             // The array of columns to return (pass null to get all)
            null,              // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            sortOrder               // The sort order
        )

        val locations = ArrayList<UserLocation>()

        while (cursor.moveToNext()) {
            val countryCode =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseEntry.DatabaseEntry.COUNTRY_CODE))
            val countryName =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseEntry.DatabaseEntry.COUNTRY_NAME))
            val cityName =
                cursor.getString(cursor.getColumnIndexOrThrow(DatabaseEntry.DatabaseEntry.CITY_NAME))

            locations.add(UserLocation(-1.0, -1.0, cityName, countryName, countryCode))
        }
        cursor.close()
        database.close()

        return locations
    }
}
