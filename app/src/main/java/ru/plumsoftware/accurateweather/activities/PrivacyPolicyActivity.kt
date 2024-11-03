package ru.plumsoftware.accurateweather.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import ru.plumsoftware.accurateweather.R

class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

//        Privacy policy
        val textView = findViewById<TextView>(R.id.textView4)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

//      Set base data
        toolbar.title = "Политика конфиденциальности"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.baseline_arrow_back_24)
        textView.movementMethod = ScrollingMovementMethod()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}