package com.example.watherapp.view

import android.annotation.SuppressLint
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.preference.PreferenceManager
import com.example.watherapp.viewmodel.CityViewModel
import com.example.watherapp.R
import com.example.watherapp.converters.TimestampToTime
import com.example.watherapp.viewmodel.WeatherViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.city_fragment.*
import kotlinx.android.synthetic.main.fragment_elders_city.*
import kotlinx.coroutines.launch
import java.lang.NullPointerException

class CityFragment : Fragment() {

    companion object {
        fun newInstance() = CityFragment()
    }
    private val viewModel: WeatherViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.city_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val defaultCity = sharedPreferences.getString("mainCity", "Kielce")


        lifecycleScope.launch{
            if(viewModel.defaultMode) {
                viewModel.selectedCity = viewModel.getWeatherForCity(defaultCity!!)
            }

            if(!viewModel.defaultMode){
           toolbarCityDetails.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
           toolbarCityDetails.setNavigationOnClickListener {
              it.findNavController().navigate(R.id.action_cityHolderFragment_to_cityList)
            } }


//            constraintLayoutEldersProgressbar.visibility = View.GONE
//            constraintLayoutElders.visibility = View.VISIBLE

            val url = "https://openweathermap.org/img/wn/${viewModel.selectedCity.weather[0].icon}@2x.png"

            Picasso.get().load(url)
                .resize(350, 350)
                .centerCrop()
                .into(imageViewCityIcon)

            textViewCityName.text = viewModel.selectedCity.name
            textViewCityTemp.text = viewModel.selectedCity.values.temp.toString() + "℃"
            textViewFeelsLikeTemp.text = viewModel.selectedCity.values.feels_like.toString() + "℃"
            textViewCityMinTemp.text = viewModel.selectedCity.values.temp_min.toString() + "℃"
            textViewCityMaxTemp.text = viewModel.selectedCity.values.temp_max.toString() + "℃"
            textViewCityHumidity.text =  viewModel.selectedCity.values.humidity.toString() + "%"
            textViewCityWind.text = viewModel.selectedCity.wind.speed.toString() + " m/s"
            textViewCityCloudiness.text = viewModel.selectedCity.clouds.all.toString() + "%"
            textViewCityCondition.text = viewModel.selectedCity.weather[0].main + " - " + viewModel.selectedCity.weather[0].description
            textViewCityPressure.text = viewModel.selectedCity.values.pressure.toString() + " hPa"
            textViewCitySunRise.text = TimestampToTime.convert(viewModel.selectedCity.sys.sunrise)
            textViewCitySunset.text = TimestampToTime.convert(viewModel.selectedCity.sys.sunset)
            textViewCityVisibility. text =  viewModel.selectedCity.visibility.toString() + " m"

            try {
                textViewCityPrecipitation.text = viewModel.selectedCity.precipitation.hour.toString() + " mm"
            }
            catch (e: NullPointerException){

            }
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


    }

}