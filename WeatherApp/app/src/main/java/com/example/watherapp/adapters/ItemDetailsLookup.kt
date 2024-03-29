package com.example.watherapp.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup

class MyItemDetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {

    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as CityListAdapter.Holder)
                .getItemDetails()
        }
        return null
    }

}