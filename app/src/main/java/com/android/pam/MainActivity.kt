package com.android.pam

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.pam.view.IMainActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IMainActivity {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_calculator.setOnClickListener { showCalculator() }
        button_astro.setOnClickListener { showAstro() }
        button_weather.setOnClickListener { showWeather() }
    }

    override fun showCalculator() {
        val launchIntent = packageManager.getLaunchIntentForPackage("com.android.calculator")
        if(launchIntent != null) {
            startActivity(launchIntent)
        } else {
            Toast.makeText(applicationContext, "Failed to start calculator", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showAstro() {
        Toast.makeText(applicationContext, "Astro is not yet implemented", Toast.LENGTH_SHORT).show()
    }

    override fun showWeather() {
        Toast.makeText(applicationContext, "Weather is not yet implemented", Toast.LENGTH_SHORT).show()
    }
}