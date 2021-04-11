package com.example.watherapp.api

import com.example.watherapp.model.entites.CityResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface IApiRequest {
    @GET("weather?")
    fun getWeatherForCity(@Query("appid") key: String,
                          @Query("units") units: String,
                          @Query("q") city: String): Call<CityResponse>
}