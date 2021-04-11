package com.example.watherapp.view

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.preference.PreferenceManager
import com.example.watherapp.R
import com.example.watherapp.converters.TimestampToTime
import com.example.watherapp.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.city_fragment.*
import kotlinx.android.synthetic.main.fragment_elders_city.*
import kotlinx.coroutines.launch

class HomeCityHolderFragment : Fragment() {

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val elders = sharedPreferences.getBoolean("eldersMode", false)
        val defaultCity = sharedPreferences.getString("mainCity", "Kielce")


        viewModel.defaultMode = true

        if (!elders){
            requireActivity().supportFragmentManager.commit {
                replace(R.id.homeFragmentContainer, CityFragment.newInstance(), "Your_TAG")
            }
        }
        else{
            requireActivity().supportFragmentManager.commit {
                replace(R.id.homeFragmentContainer, EldersCityFragment.newInstance(), "Your_TAG")
            }
        }


        return inflater.inflate(R.layout.fragment_home_city_holder, container, false)
    }

    companion object {
        fun newInstance() = HomeCityHolderFragment().apply {}
    }
}