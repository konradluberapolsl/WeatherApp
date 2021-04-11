package com.example.watherapp.view

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.example.watherapp.R
import com.example.watherapp.converters.TimestampToTime
import com.example.watherapp.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_elders_city.*
import kotlinx.coroutines.launch

class EldersCityFragment : Fragment() {

    private val viewModel: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_elders_city, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!viewModel.defaultMode){
            toolbarEldersCity.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
            toolbarEldersCity.setNavigationOnClickListener {
                it.findNavController().navigate(R.id.action_cityHolderFragment_to_cityList)
            }
        }

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val defaultCity = sharedPreferences.getString("mainCity", "Kielce")


        lifecycleScope.launch{
           if(viewModel.defaultMode) {
              viewModel.selectedCity = viewModel.getWeatherForCity(defaultCity!!)
           }

            constraintLayoutEldersProgressbar.visibility = View.GONE
            constraintLayoutElders.visibility = View.VISIBLE

            val url = "https://openweathermap.org/img/wn/${viewModel.selectedCity.weather[0].icon}@2x.png"

            Picasso.get().load(url)
                .resize(400, 400)
                .into(imageViewEldersIcon)

            textViewEldersCityName.text = viewModel.selectedCity.name
            textViewEldersTemp.text = viewModel.selectedCity.values.temp.toString() + "℃"
            textViewEldersConditions.text = viewModel.selectedCity.weather[0].main
            textViewEldersPressure.text = viewModel.selectedCity.values.pressure.toString() + " hPa"
            textViewEldersSunrise.text = TimestampToTime.convert(viewModel.selectedCity.sys.sunrise)
            textViewEldersSunset.text = TimestampToTime.convert(viewModel.selectedCity.sys.sunset)
        }





    }

    companion object {
        fun newInstance() = EldersCityFragment()

    }
}