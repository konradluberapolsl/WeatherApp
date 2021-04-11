package com.example.watherapp.api

import com.example.watherapp.model.entites.CityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

class ApiRoutes {
    companion object{
        const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        const val APP_KEY = "790a4d853539e866b1edef2bae8a937e"
    }
}