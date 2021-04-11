package com.example.watherapp.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.watherapp.api.ApiRoutes
import com.example.watherapp.api.IApiRequest
import com.example.watherapp.helpers.PreferencesConfig
import com.example.watherapp.model.entites.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {

    var weatherList = MutableLiveData<MutableList<CityResponse>>()
    var cityList = MutableLiveData<MutableList<String>>()
    var selectedCities = mutableListOf<CityResponse>()
    private val api = Retrofit.Builder().baseUrl(ApiRoutes.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()
        .create(IApiRequest::class.java)

    var defaultMode = true
    lateinit var selectedCity : CityResponse

    fun getCityList(context: Context){
        cityList.postValue(PreferencesConfig.readListFromPreferences(context))
    }

    fun getWeatherForCities(){
        GlobalScope.launch(Dispatchers.IO){

            val tmplist = mutableListOf<CityResponse>()

            for (city in cityList.value!!){
                val response = api.getWeatherForCity(ApiRoutes.APP_KEY, "metric", city)
                    .awaitResponse()

                if (response.isSuccessful) {
                    var data = response.body()
                    if (data != null) {
                        tmplist.add(data)
                        Log.d("MyTag",data.toString())
                    }
//                    Thread.sleep(5000)
                } else {
                    Log.d("api-connection", "response failed")
                }

            }
            Log.d("MyTag", tmplist.toString())
            weatherList.postValue(tmplist)
        }
    }

    suspend fun getWeatherForCity(name: String): CityResponse{
            val response = api.getWeatherForCity(ApiRoutes.APP_KEY, "metric", name)
                .awaitResponse()

            if (response.isSuccessful) {
                var data = response.body()
                if (data != null) {
                    Log.d("MyTag","Success")
                    selectedCity = data
                   return data
                }
            } else {
                Log.d("api-connection", "response failed")

            }
        return CityResponse("", listOf(),0,
            Values(0.0,0.0,0.0,0.0,0,0),
            Precipitation(0.0), Clouds(0), Wind(0.0,0,0.0),
            Sys(0,0))
    }

    fun deleteCity(context: Context){
        for (city in selectedCities){
            cityList.value?.remove(city.name)
        }
        PreferencesConfig.writeListInPreferences(context, cityList.value!!)
        getCityList(context)
    }

    fun addCity(name: String, context: Context, successCallback: ((c: Boolean) -> Unit)){
        GlobalScope.launch(Dispatchers.IO){

                val response = api.getWeatherForCity(ApiRoutes.APP_KEY, "metric", name)
                    .awaitResponse()

                if (response.isSuccessful) {
                    var data = response.body()
                    if (data != null) {
                        cityList.value?.add(name)
                        PreferencesConfig.writeListInPreferences(context, cityList.value!!)
                        getCityList(context)
                        successCallback(true)
                    }
                } else {
                    Log.d("api-connection", "response failed")
                    successCallback(false)
                }

            }

        }
    }
