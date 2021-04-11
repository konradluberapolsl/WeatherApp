package com.example.watherapp.model.entites

import com.google.gson.annotations.SerializedName

data class Precipitation(@SerializedName("1h") val  hour : Double?) {
}