package com.example.watherapp.model.entites
import com.google.gson.annotations.SerializedName

data class CityResponse(val name: String,
                        val weather: List<Weather>,
                        val visibility : Int,
                        @SerializedName("main")
                        val values: Values,
                        @SerializedName(value = "rain", alternate = ["snow"])
                        val precipitation: Precipitation,
                        val clouds: Clouds,
                        val wind: Wind,
                        val sys: Sys) {
}