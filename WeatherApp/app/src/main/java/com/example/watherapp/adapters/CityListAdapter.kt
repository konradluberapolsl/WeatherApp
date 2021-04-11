package com.example.watherapp.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.example.watherapp.R
import com.example.watherapp.model.entites.CityResponse
import com.squareup.picasso.Picasso

class CityListAdapter(var data: MutableLiveData<MutableList<CityResponse>>,
                      var clickCallback : (c: CityResponse) -> Unit)
    :RecyclerView.Adapter<CityListAdapter.Holder>() {

    var tracker: SelectionTracker<Long>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cities_row,parent,false)

        return Holder(view)
    }

    init {
        setHasStableIds(true)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val textViewCityName = holder.itemView.findViewById<TextView>(R.id.textViewCityListName)
        val textViewCityConditions = holder.itemView.findViewById<TextView>(R.id.textViewcityListConditions)
        val textViewCityTemp = holder.itemView.findViewById<TextView>(R.id.textViewCityListTemp)
        val imageView = holder.itemView.findViewById<ImageView>(R.id.imageViewCityList)
        val constraintLayoutCityRow = holder.itemView.findViewById<ConstraintLayout>(R.id.constraintLayoutCityRow)
        val url = "https://openweathermap.org/img/wn/${data.value?.get(position)?.weather?.get(0)?.icon}@2x.png"

        Picasso.get().load(url)
            .resize(250, 250)
            .into(imageView)

        val conditions = data.value?.get(position)?.weather?.get(0)?.main

        textViewCityName.text = data.value?.get(position)?.name
        textViewCityConditions.text = conditions
        textViewCityTemp.text = data.value?.get(position)?.values?.temp.toString() + "℃"

        constraintLayoutCityRow.setOnClickListener {
            clickCallback(data.value?.get(position)!!)
            it.findNavController().navigate(R.id.action_cityList_to_cityHolderFragment)
        }

        tracker?.let {

            holder.bind(it.isSelected(position.toLong()))
        }

    }

    override fun getItemCount(): Int {
        return data.value?.size?:0
    }

    override fun getItemId(position: Int): Long = position.toLong()

    inner class Holder(view: View): RecyclerView.ViewHolder(view){
        private var constraintLayout = view.findViewById<ConstraintLayout>(R.id.constraintLayoutCityRow)

        fun bind(isActivated: Boolean) {
            constraintLayout.isActivated = isActivated
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }
    }

}