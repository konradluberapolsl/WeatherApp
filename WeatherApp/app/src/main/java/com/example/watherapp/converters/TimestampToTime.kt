package com.example.watherapp.converters

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class TimestampToTime {
    companion object{
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SimpleDateFormat")
        fun convert(timestamp: Int): String{

            val dtf = DateTimeFormatter.ofPattern("hh:mm a")

            val time =  Instant.ofEpochSecond(timestamp.toLong())
                .atZone(ZoneId.systemDefault())
                .toLocalTime().format(dtf).toString()


            return time
        }
    }
}