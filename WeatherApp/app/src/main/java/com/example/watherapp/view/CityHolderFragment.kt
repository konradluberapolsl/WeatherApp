package com.example.watherapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.commit
import androidx.preference.PreferenceManager
import com.example.watherapp.R

class CityHolderFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        val elders = sharedPreferences.getBoolean("eldersMode", false)

        if (!elders){
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragmentContainer, CityFragment.newInstance(), "Your_TAG")
            }
        }
        else{
            requireActivity().supportFragmentManager.commit {
                replace(R.id.fragmentContainer, EldersCityFragment.newInstance(), "Your_TAG")
            }
        }


        return inflater.inflate(R.layout.fragment_city_holder, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = CityHolderFragment()
    }
}