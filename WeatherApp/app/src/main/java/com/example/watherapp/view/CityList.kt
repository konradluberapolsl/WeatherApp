package com.example.watherapp.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.watherapp.R
import com.example.watherapp.viewmodel.CityListViewModel

class CityList : Fragment() {

    companion object {
        fun newInstance() = CityList()
    }

    private lateinit var viewModel: CityListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.city_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CityListViewModel::class.java)
        // TODO: Use the ViewModel
    }

}