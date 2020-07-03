package com.android.pam.astro_weather.presentation.view.view_holder

import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.pam.astro_weather.R
import com.android.pam.astro_weather.domain.model.location.City
import com.android.pam.astro_weather.domain.model.location.Coordinates

class CityViewHolder(view: View)
    : RecyclerView.ViewHolder(view) {
    val cityItem: City?
        get() = try {
            City(city_id!!, city!!, state!!, country!!, Coordinates(longitude, latitude), favourite!!)
        } catch (e: KotlinNullPointerException) {
            null
        }

    var city_id: Long? = null
        set(value) {
            field = value
            city_id_txv?.text = itemView.resources.getString(R.string.fc_id_format).format(
                value?.toString().orEmpty()
            )
        }

    var city: String? = null
        set(value) {
            field = value
            city_txv?.text = value.orEmpty()
        }

    var state: String? = null
        set(value) {
            field = value
            state_txv?.text = value.orEmpty()
        }

    var country: String? = null
        set(value) {
            field = value
            country_txv?.text = value.orEmpty()
        }

    var latitude: Double? = null
        set(value) {
            field = value
            latitude_txv?.text = itemView.resources.getString(R.string.fc_latitude_short).format(
                value?.toString().orEmpty()
            )
        }

    var longitude: Double? = null
        set(value) {
            field = value
            longitude_txv?.text = itemView.resources.getString(R.string.fc_longitude_short).format(
                value?.toString().orEmpty()
            )
        }

    var favourite: Boolean? = null
        set(value) {
            field = value
            itemView.setBackgroundColor(
                if(favourite == true)
                    ContextCompat.getColor(itemView.context, R.color.colorAccent)
                else
                    ContextCompat.getColor(itemView.context, R.color.colorBackground)
            )
        }

    private var city_id_txv: TextView? = null
    private var city_txv: TextView? = null
    private var state_txv: TextView? = null
    private var country_txv: TextView? = null
    private var latitude_txv: TextView? = null
    private var longitude_txv: TextView? = null

    init {
        city_id_txv = itemView.findViewById(R.id.sfcItem_txv_cityId)
        city_txv = itemView.findViewById(R.id.sfcItem_txv_city)
        state_txv = itemView.findViewById(R.id.sfcItem_txv_state)
        country_txv = itemView.findViewById(R.id.sfcItem_txv_country)
        latitude_txv = itemView.findViewById(R.id.sfcItem_txv_latitude)
        longitude_txv = itemView.findViewById(R.id.sfcItem_txv_longitude)
    }

    fun bind(item: City) {
        city_id = item.id
        city = item.name
        state = item.state
        country = item.country
        latitude = item.coordinates.latitude
        longitude = item.coordinates.longitude
        favourite = item.favourite
    }
}