package com.example.watherapp.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.watherapp.R
import com.example.watherapp.helpers.PreferencesConfig
import com.facebook.drawee.backends.pipeline.Fresco
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPref = this.getPreferences(Context.MODE_PRIVATE)

        val mainCity = sharedPref.getString("mainCity", "Gliwice")

        if(!sharedPref.contains("city_list")){
            PreferencesConfig.writeListInPreferences(applicationContext, listOf(mainCity!!))
        }


        val navController = findNavController(R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)

    }
}