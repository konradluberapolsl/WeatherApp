package com.example.watherapp.api

import com.example.watherapp.model.entites.CityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiRequest {
    @GET
    fun getWeatherForCity(@Query("q") city: String): Call<CityResponse>
}