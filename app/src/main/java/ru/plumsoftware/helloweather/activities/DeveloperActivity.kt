package ru.plumsoftware.helloweather.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ru.plumsoftware.helloweather.R

class DeveloperActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_developer)
        Toast.makeText(this, "Скоро!", Toast.LENGTH_LONG).show()
    }
}