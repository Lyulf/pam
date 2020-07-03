package com.android.pam.astro_weather.presentation.view.fragment

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.android.pam.astro_weather.R
import com.android.pam.astro_weather.domain.model.settings.Settings
import com.android.pam.astro_weather.domain.model.weather.DailyForecast
import com.android.pam.astro_weather.presentation.contract.IAstroWeatherContract
import com.android.pam.astro_weather.presentation.contract.IForecastContract
import com.android.pam.astro_weather.presentation.view.view_holder.DailyForecastViewHolder
import kotlinx.android.synthetic.main.fragment_forecast.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class ForecastFragment : BaseFragment(), IForecastContract.IView {
    @Inject lateinit var presenter: IForecastContract.IPresenter
    @Inject lateinit var viewModel: IForecastContract.IViewModel

    private var units: Settings.Units? = null

    private var adapter: ListAdapter? = null
    private var forecasts: List<DailyForecast> = listOf()

    private val unitsObserver = Observer<Settings.Units> {
        units = it
        updateData()
    }

    private val forecastObserver = Observer<List<DailyForecast>> {
        forecasts = it ?: listOf()
        updateData()
    }

    override fun onAttach(context: Context) {
        (activity as IAstroWeatherContract.IView).astroWeatherComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_forecast, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            presenter.onAttach(this@ForecastFragment)

            adapter = ListAdapter(listOf(), units, presenter)
            val manager = GridLayoutManager(context, 2)
            val spacingInPixels: Int = resources.getDimensionPixelOffset(R.dimen.spacing)
            forecastFragment_recv.addItemDecoration(SpacesItemDecoration(spacingInPixels))
            forecastFragment_recv.layoutManager = manager
            forecastFragment_recv.adapter = adapter
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun subscribeForecasts() {
        viewModel.dailyForecast().observe(viewLifecycleOwner, forecastObserver)
        viewModel.units().observe(viewLifecycleOwner, unitsObserver)
    }

    override fun unsubscribeForecasts() {
        viewModel.dailyForecast().removeObserver { forecastObserver }
        viewModel.units().removeObserver { unitsObserver }
    }

    private fun updateData() = launch {
        adapter?.units = units
        adapter?.setItems(forecasts)
    }

    class SpacesItemDecoration(private val space: Int) : ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            super.getItemOffsets(outRect, view, parent, state)

            outRect.left = space
            outRect.right = space
            outRect.bottom = space

            if (parent.getChildLayoutPosition(view) == 0 || parent.getChildLayoutPosition(view) == 1) {
                outRect.top = space
            } else {
                outRect.top = 0
            }
        }

    }

    class ListAdapter(
        private var items: List<DailyForecast>,
        var units: Settings.Units?,
        private val presenter: IForecastContract.IPresenter
    ) : RecyclerView.Adapter<DailyForecastViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.data_forecast, parent, false)
            return DailyForecastViewHolder(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
            val item = items[position]
            holder.bind(item, units)
        }

        fun setItems(items: List<DailyForecast>) {
            this.items = items
            notifyDataSetChanged()
        }
    }
}