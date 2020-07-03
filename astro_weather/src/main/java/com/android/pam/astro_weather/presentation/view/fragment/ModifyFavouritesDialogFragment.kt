package com.android.pam.astro_weather.presentation.view.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.pam.astro_weather.R
import com.android.pam.astro_weather.domain.model.location.City
import com.android.pam.astro_weather.presentation.contract.IFavouritesContract
import com.android.pam.astro_weather.presentation.contract.ISettingsContract
import com.android.pam.astro_weather.presentation.view.view_holder.CityViewHolder
import kotlinx.android.synthetic.main.fragment_set_favourite_cities.*
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ModifyFavouritesDialogFragment : DialogFragment(), CoroutineScope, IFavouritesContract.IView {
    @Inject lateinit var presenter: IFavouritesContract.IPresenter
    @Inject lateinit var viewModel: IFavouritesContract.IViewModel

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val citiesObserver = Observer<List<City>> {
        updateData(ArrayList(it))
    }

    private var favouriteAdapter: ArrayAdapter<String>? = null
    private var citiesAdapter: ArrayListAdapter? = null

    private val favouriteStates = arrayOf(
        "Any",
        "Off",
        "On"
    )

    override fun onAttach(context: Context) {
        (activity as ISettingsContract.IView).settingsComponent().inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_set_favourite_cities, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launch {
            citiesAdapter = ArrayListAdapter(arrayListOf(), presenter)
            val manager = LinearLayoutManager(context)
            manager.reverseLayout = false
            sfcFragment_recv_cities.layoutManager = manager
            sfcFragment_recv_cities.adapter = citiesAdapter

            val attachmentJob = launch {
                presenter.onAttach(this@ModifyFavouritesDialogFragment)
            }

            favouriteAdapter = ArrayAdapter(
                this@ModifyFavouritesDialogFragment.context!!,
                R.layout.support_simple_spinner_dropdown_item,
                favouriteStates
            )
            sfcFragment_spn_favourite.adapter = favouriteAdapter
            sfcFragment_spn_favourite.setSelection(0)

            attachmentJob.join()
            sfcFragment_btn_search.setOnClickListener {
                onSearch()
            }

            sfcFragment_btn_finish.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun onSearch() = launch {
        Toast.makeText(context, "Searching...", Toast.LENGTH_SHORT).show()
        var temp = sfcFragment_edt_city.text.toString()
        val city = if(temp.isNotBlank()) temp else null
        temp = sfcFragment_edt_state.text.toString()
        val state = if(temp.isNotBlank()) temp else null
        temp = sfcFragment_edt_country.text.toString()
        val country = if(temp.isNotBlank()) temp else null
        temp = sfcFragment_edt_id.text.toString()
        val favourite = when(sfcFragment_spn_favourite.selectedItemPosition) {
            1 -> false
            2 -> true
            else -> null
        }
        val id = if(temp.isNotBlank()) temp.toLong() else null
        presenter.onFindCities(
            city, state, country, favourite, id
        )

    }

    override fun displaySearchFinished() {
        Toast.makeText(context, "Search finished", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        presenter.onDetach()
        super.onDestroyView()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        presenter.onFinish()
    }

    override fun subscribeCities() {
        viewModel.cities().observe(viewLifecycleOwner, citiesObserver)
    }

    override fun unsubscribeCities() {
        viewModel.cities().removeObserver { citiesObserver }
    }

    private fun updateData(values: ArrayList<City>) = launch {
        citiesAdapter?.setItems(values)
    }

    class ArrayListAdapter(
        private var items: ArrayList<City>,
        private val presenter: IFavouritesContract.IPresenter
    ) : RecyclerView.Adapter<CityViewHolder>(), CoroutineScope {

        private val job = Job()
        override val coroutineContext: CoroutineContext
            get() = job + Dispatchers.Main

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = inflater.inflate(R.layout.data_city, parent, false)
            return CityViewHolder(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
            val item = items[position]
            holder.bind(item)
            holder.itemView.setOnClickListener {
                onClickItem(position)
            }
        }

        private fun onClickItem(position: Int) {
            launch {
                items[position].let {
                    it.favourite = !it.favourite
                    withContext(Dispatchers.IO) { presenter.setCity(it) }
                    notifyItemChanged(position)
                }
            }
        }

        fun setItems(items: ArrayList<City>) {
            this.items = items
            notifyDataSetChanged()
        }
    }
}