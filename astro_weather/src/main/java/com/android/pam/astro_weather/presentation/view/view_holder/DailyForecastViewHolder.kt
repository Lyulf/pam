package com.android.pam.astro_weather.presentation.view.view_holder

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.pam.astro_weather.R
import com.android.pam.astro_weather.domain.model.settings.Settings
import com.android.pam.astro_weather.domain.model.weather.DailyForecast
import com.android.pam.astro_weather.presentation.converter.UnitsConverter
import com.android.pam.astro_weather.presentation.extensions.ImageViewExtensions.setImage
import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter

class DailyForecastViewHolder(view: View)
    : RecyclerView.ViewHolder(view) {
    private var day_txv: TextView? = null
    private var temperature_txv: TextView? = null
    private var description_txv: TextView? = null
    private var icon_imgv: ImageView? = null

    init {
        day_txv = itemView.findViewById(R.id.forecastItem_txv_day)
        temperature_txv = itemView.findViewById(R.id.forecastItem_txv_temperature)
        description_txv = itemView.findViewById(R.id.forecastItem_txv_description)
        icon_imgv = itemView.findViewById(R.id.forecastItem_imgv_icon)
    }

    var unitsConverter: UnitsConverter? = null

    var day: LocalDate? = null
        set(value) {
            field = value
            val formatter = DateTimeFormatter.ofPattern("EEEE")
            day_txv?.text = if(value != null) formatter.format(value) else ""
        }

    var temp_day: Float? = null
        set(value) {
            field = value
            updateTemp()
        }

    var temp_night: Float? = null
        set(value) {
            field = value
            updateTemp()
        }

    var icon: String? = null
        set(value) {
            field = value
            if(value != null) {
                icon_imgv?.setImage("ic_$value", "drawable", itemView.context.packageName)
                icon_imgv?.visibility = VISIBLE
            } else {
                icon_imgv?.visibility = GONE
            }
        }

    var description: String? = null
        set(value) {
            field = value
            description_txv?.text = value ?: ""
        }

    fun bind(item: DailyForecast, units: Settings.Units?) {
        unitsConverter = units?.let { UnitsConverter(it) }
        day = item.date
        temp_day = item.temp_day
        temp_night = item.temp_night
        description = item.description
        icon = item.icon
    }

    private fun updateTemp() {
        var temp = ""
        val temp_day = unitsConverter?.temperature(temp_day)
        val temp_night = unitsConverter?.temperature(temp_night)
        if(temp_day != null && temp_night != null) {
            temp = "$temp_day / $temp_night"
        } else {
            temp_day?.let {
                temp = it
            }
            temp_night?.let {
                temp = it
            }
        }
        temperature_txv?.text = temp
    }
}