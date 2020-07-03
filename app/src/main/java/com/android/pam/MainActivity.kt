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
        button_astroWeather.setOnClickListener { showAstroWeather() }
    }

    override fun showCalculator() {
        val launchIntent = packageManager.getLaunchIntentForPackage("com.android.calculator")
        if(launchIntent != null) {
            startActivity(launchIntent)
        } else {
            Toast.makeText(applicationContext, "Failed to start calculator", Toast.LENGTH_SHORT).show()
        }
    }

    override fun showAstroWeather() {
        val launchIntent = packageManager.getLaunchIntentForPackage("com.android.astro_weather")
        if(launchIntent != null) {
            startActivity(launchIntent)
        } else {
            Toast.makeText(applicationContext, "Failed to start AstroWeather", Toast.LENGTH_SHORT).show()
        }
    }
}