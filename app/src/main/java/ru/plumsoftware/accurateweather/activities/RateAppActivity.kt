package ru.plumsoftware.accurateweather.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.plumsoftware.accurateweather.R
import ru.plumsoftware.accurateweather.basedata.BaseNames

class RateAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rate_app)
        openWebPage()
        finish()
    }

    private fun openWebPage(url: String = BaseNames.RUSTORE_URL) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}