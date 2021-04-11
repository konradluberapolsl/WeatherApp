package com.example.watherapp.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.watherapp.R
import com.example.watherapp.viewmodel.WeatherViewModel
import kotlinx.android.synthetic.main.city_dialog.*

class CityNameDialog : DialogFragment() {

    private val viewModel: WeatherViewModel by activityViewModels()
    private lateinit var editTextCity: EditText
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(context)
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.city_dialog, null)
        editTextCity = view.findViewById(R.id.editTextCity)

        builder.setView(view).setTitle(getString(R.string.dialog_title))
            .setNegativeButton(getString(R.string.cancel), DialogInterface.OnClickListener { dialog, id ->

            })
            .setPositiveButton(getString(R.string.add), DialogInterface.OnClickListener { dialog, id ->
                viewModel.addCity(editTextCity.text.toString(), requireContext()){
                    if (!it){
//                        Toast.makeText(ac, getString(R.string.error), Toast.LENGTH_SHORT).show()
                    }
                }
                Log.d("myTag", editTextCity.text.toString())
            })

        return builder.create()
    }

}