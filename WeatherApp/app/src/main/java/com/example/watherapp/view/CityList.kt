package com.example.watherapp.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.watherapp.R
import com.example.watherapp.adapters.CityListAdapter
import com.example.watherapp.adapters.MyItemDetailsLookup
import com.example.watherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.city_list_fragment.*

class CityList : Fragment() {

    companion object {
        fun newInstance() = CityList()
    }

    private val viewModel: WeatherViewModel by activityViewModels()
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var cityListAdapter: CityListAdapter
    private lateinit var tracker: SelectionTracker<Long>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        cityListAdapter = CityListAdapter(viewModel.weatherList){
            viewModel.defaultMode = false
            viewModel.selectedCity = it
        }
        viewManager = LinearLayoutManager(requireContext())

        viewModel.getCityList(requireContext())

        viewModel.cityList.observe(viewLifecycleOwner){
            viewModel.getWeatherForCities()
        }

        viewModel.weatherList.observe(viewLifecycleOwner){
            progressBarCityList.visibility = View.GONE
            recyclerViewCities.visibility = View.VISIBLE
            cityListAdapter.notifyDataSetChanged()
        }

        return inflater.inflate(R.layout.city_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewCities.apply{
            adapter =  cityListAdapter
            layoutManager=viewManager
        }

        setupTracker()

        topBarCityList.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.cityRemove -> {
                    if (tracker.selection.size() != 0){
                        viewModel.selectedCities = tracker.selection.map {
                            cityListAdapter.data.value!!.get(it.toInt())
                        }.toMutableList()
                    }
                    tracker.clearSelection()
                    viewModel.deleteCity(requireContext())
                    true
                }
                R.id.citAdd -> {
                    openDialog()
                    true
                }
                else -> false
            }
        }


    }

    private fun setupTracker() {

        tracker = SelectionTracker.Builder<Long>(
            "mySelection",
            recyclerViewCities,
            StableIdKeyProvider(recyclerViewCities),
            MyItemDetailsLookup(recyclerViewCities),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        tracker.addObserver(
            object : SelectionTracker.SelectionObserver<Long>() {
                override fun onSelectionChanged() {
                    super.onSelectionChanged()
                }
            })

        cityListAdapter.tracker = tracker
    }

    private fun openDialog(){
        val cityNameDialog = CityNameDialog()
        cityNameDialog.show(requireActivity().supportFragmentManager, "dialog")
    }

}