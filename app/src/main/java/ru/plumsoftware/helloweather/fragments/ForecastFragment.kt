package ru.plumsoftware.helloweather.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.plumsoftware.helloweather.*
import ru.plumsoftware.helloweather.MainActivity.Companion.badIconToGoodIcon
import ru.plumsoftware.helloweather.MainActivity.Companion.convertPressure
import ru.plumsoftware.helloweather.MainActivity.Companion.metersPerSecondToBeaufort
import ru.plumsoftware.helloweather.MainActivity.Companion.mphToBeaufort
import ru.plumsoftware.helloweather.MainActivity.Companion.windDirectionFull
import ru.plumsoftware.helloweather.adapters.FragmentAdapter
import ru.plumsoftware.helloweather.basedata.BaseNames
import ru.plumsoftware.helloweather.basedata.BaseUnits
import ru.plumsoftware.helloweather.forecastdata.CurrentWeatherForecast
import ru.plumsoftware.helloweather.retrofitmethods.RequestsMethods
import java.io.File
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

var speed: Double? = 0.0

class ForecastFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        Settings
        MainActivity.loadSettings()

//        Variables
        val view = inflater.inflate(R.layout.fragment_forecast, container, false)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val create: RequestsMethods = retrofit.create(RequestsMethods::class.java)
        var isNight: Boolean = false
        var isExpanded: Boolean = false
        val calendar = Calendar.getInstance()

//        Views
        val toolbar = view.findViewById<Toolbar>(R.id.toolbar)

        val mainTempView = view.findViewById<TextView>(R.id.mainTempView)
        val feelsLikeTemp = view.findViewById<TextView>(R.id.feelsLikeTemp)
        val weatherLogoView = view.findViewById<ImageView>(R.id.weatherLogoView)
        val someData1 = view.findViewById<TextView>(R.id.textViewSomedata1)
        val someData2 = view.findViewById<TextView>(R.id.textViewSomedata2)
        val someData3 = view.findViewById<TextView>(R.id.textViewSomedata3)
        val someData4 = view.findViewById<TextView>(R.id.textViewSomedata4)

        val textViewW1 = view.findViewById<TextView>(R.id.textViewW1)
        val textViewW2 = view.findViewById<TextView>(R.id.textViewW2)

        val viewPager = view.findViewById<ViewPager2>(R.id.viewPager)

//        val indicator = view.findViewById<CircleIndicator3>(R.id.indicator)

        val card = view.findViewById<CardView>(R.id.card)

        val l1 = view.findViewById<LinearLayout>(R.id.l1)
        val l2 = view.findViewById<LinearLayout>(R.id.l2)

//        Set base data
//        indicator.setViewPager(viewPager)
        l2.visibility = View.GONE
        isNight = calendar.get(Calendar.HOUR_OF_DAY) > 22 //Ночь - это время позднее 22:00
        toolbar.title = sharedPreferences?.getString(
            BaseNames.SETTINGS_LIST_VALUE[5],
            "Москва"
        ) + ", " + sharedPreferences?.getString(BaseNames.SETTINGS_LIST_VALUE[6], "Россия")

//        Requests
        val currentWeather: Call<CurrentWeatherForecast> = create.getCurrentWeather(
            sharedPreferences?.getString(BaseNames.SETTINGS_LIST_VALUE[5], "Москва")!!,
            BaseNames.APP_ID,
            units_value!!,
            "ru"
        )

        currentWeather.enqueue(object : Callback<CurrentWeatherForecast> {
            override fun onFailure(call: Call<CurrentWeatherForecast>, t: Throwable) {
                Toast.makeText(requireContext(), t.message, Toast.LENGTH_LONG).show()
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<CurrentWeatherForecast>,
                response: Response<CurrentWeatherForecast>
            ) {
                val body: CurrentWeatherForecast? = response.body()

//                Setup weather data
                textViewW2.text = "Ветер " +
                        if (units_value == BaseUnits.units_value[0]) {
                            speed = body?.wind?.speed
                            if (windSpeed_value == BaseUnits.wind_speed_value[2]) {
                                metersPerSecondToBeaufort(speed!!).toInt()
                                    .toString() + " " + windSpeed
                            } else {
                                speed?.times(windSpeed_value)?.toInt().toString() + " " + windSpeed
                            }
                        } else {
                            speed = body?.wind?.speed
                            when (windSpeed) {
                                BaseUnits.wind_speed[0] -> {
                                    speed?.times(0.44704)?.toInt().toString() + " " + windSpeed
                                }
                                BaseUnits.wind_speed[1] -> {
                                    speed?.times(0.868976)?.toInt().toString() + " " + windSpeed
                                }
                                BaseUnits.wind_speed[2] -> {
                                    mphToBeaufort(speed!!).toInt().toString() + " " + windSpeed
                                }
                                else -> {
                                    speed?.times(windSpeed_value)?.toInt()
                                        .toString() + " " + windSpeed
                                }
                            }
                        } + " с " + windDirectionFull(body?.wind?.deg?.toInt()!!).lowercase()
//                Glide.with(requireContext()).load(badIconToGoodIcon(body!!.weather[0].id!!, isNight))
//                    .into(weatherLogoView)

//                Temperature
                mainTempView.text = body.main?.temp?.toInt().toString() + "°"
                feelsLikeTemp.text =
                    "Чувствуется как " + body.main?.feelsLike?.toInt().toString() + "°"
                weatherLogoView.setImageResource(badIconToGoodIcon(body.weather[0].id!!, isNight))

//                Description
                textViewW1.text = body.weather[0].description

//                Another data
                someData1.text = "Видимость " + body.visibility + "+" + when (units_value) {
                    BaseUnits.units_value[0] -> " километров."
                    BaseUnits.units_value[1] -> " миль."
                    else -> {
                        " километров."
                    }
                }
                someData2.text = "Влажность " + body.main?.humidity + "%."

                someData3.text = "Восход " +
                        SimpleDateFormat(
                            pattern,
                            Locale.getDefault()
                            /**Время в unix формате**/
                        ).format(Date((body.sys?.sunrise?.times(1000L))!!.toLong())) + " → Закат " + SimpleDateFormat(
                    pattern,
                    Locale.getDefault()
                ).format(Date((body.sys?.sunset?.times(1000L))!!.toLong())) + "\n" +
                        SimpleDateFormat(
                            pattern,
                            Locale.getDefault()
                        ).format(Date((body.dt?.times(1000L))!!.toLong())) + " часов дневного времени."

                someData4.text =
                    "Давление " + convertPressure(body.main?.pressure!!) + " " + barometricPressure
            }


        })

        viewPager.adapter = FragmentAdapter(requireContext(), requireActivity())

//        Clickers
        toolbar.setOnMenuItemClickListener {
            onOptionsItemSelected(it)
        }
        l1.setOnClickListener {
            if (isExpanded) {
                TransitionManager.beginDelayedTransition(card, AutoTransition())
                l1.visibility = View.VISIBLE
                l2.visibility = View.GONE
                isExpanded = false
            } else {
                TransitionManager.beginDelayedTransition(card, AutoTransition())
                l2.visibility = View.VISIBLE
                l1.visibility = View.GONE
                isExpanded = true
            }
        }
        l2.setOnClickListener {
            if (isExpanded) {
                TransitionManager.beginDelayedTransition(card, AutoTransition())
                l1.visibility = View.VISIBLE
                l2.visibility = View.GONE
                isExpanded = false
            } else {
                TransitionManager.beginDelayedTransition(card, AutoTransition())
                l2.visibility = View.VISIBLE
                l1.visibility = View.GONE
                isExpanded = true
            }
        }
        return view
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.share -> {
                takeAndShareScreenshot()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /** HERE **/
    @Suppress("DEPRECATION")
    private fun takeScreenshot(view: View): Bitmap {
        view.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false
        return bitmap
    }

    private fun saveScreenshot(bitmap: Bitmap, fileName: String) {
        val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            .toString() + "/$fileName.png"
        val file = File(filePath)
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        outputStream.flush()
        outputStream.close()
    }

    private fun shareScreenshot(fileName: String) {
        val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            .toString() + "/$fileName.png"
        val fileProvider = requireContext().packageName + ".provider"
        val uri = FileProvider.getUriForFile(requireContext(), fileProvider, File(filePath))
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        startActivity(Intent.createChooser(intent, "Share screenshot"))
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), 100)
        }
    }

    private fun takeAndShareScreenshot() {
        checkPermissions()
        val rootView = requireActivity().window.decorView.findViewById<View>(android.R.id.content)
        val screenshot = takeScreenshot(rootView)
        val timestamp = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.getDefault()).format(Date())
        val fileName = "screenshot-$timestamp"
        saveScreenshot(screenshot, fileName)
        shareScreenshot(fileName)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val rootView =
                requireActivity().window.decorView.findViewById<View>(android.R.id.content)
            val screenshot = takeScreenshot(rootView)
            val timestamp = SimpleDateFormat("yyyyMMdd-HHmmss", Locale.getDefault()).format(Date())
            val fileName = "screenshot-$timestamp"
            saveScreenshot(screenshot, fileName)
            shareScreenshot(fileName)
        } else {
            Toast.makeText(requireContext(), "Нет доступа к хранилищу.", Toast.LENGTH_SHORT).show()
        }
    }
}
