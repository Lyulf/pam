package com.android.pam.astro_weather

import com.android.pam.astro_weather.data.entity.weather.OneCall
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldNotBe
import java.net.URL

class WeatherUnitTest : StringSpec({
    "Weather should not be null" {
        val response = URL(
            "https://api.openweathermap.org/data/2.5/onecall?lat=33.441792&lon=-94.037689&" +
                "exclude=minutely,hourly&appid=f8a1c5d8eba81dbe07efa61c35510bf6").readText()
        println(response)
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val adapter = moshi.adapter(OneCall::class.java)
        adapter.fromJson(response) shouldNotBe null
    }
})