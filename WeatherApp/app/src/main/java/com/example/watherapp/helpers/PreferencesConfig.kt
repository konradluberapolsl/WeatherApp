package com.example.watherapp.helpers

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PreferencesConfig {

    companion object{

        private val listKey = "city_list"

        fun writeListInPreferences(context: Context, cityList : List<String> ){
           val gson = Gson()
           val jsonList = gson.toJson(cityList)
           val sharedPref = context.getSharedPreferences(listKey, Context.MODE_PRIVATE)
           sharedPref.edit().putString(listKey,jsonList).apply()
        }

        fun readListFromPreferences(context: Context) : MutableList<String>{
            val gson = Gson()
            val itemType = object : TypeToken<MutableList<String>>() {}.type
            val sharedPref = context.getSharedPreferences(listKey, Context.MODE_PRIVATE)
            val jsonList = sharedPref.getString(listKey, "")

            return gson.fromJson(jsonList, itemType)
        }
    }
}