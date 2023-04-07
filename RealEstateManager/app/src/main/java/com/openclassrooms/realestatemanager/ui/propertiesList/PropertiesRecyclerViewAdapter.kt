package com.openclassrooms.realestatemanager.ui.propertiesList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Image
import com.openclassrooms.realestatemanager.model.Property

class PropertiesRecyclerViewAdapter(private val propertiesList : List<Property>) :
    RecyclerView.Adapter<PropertiesRecyclerViewAdapter.PropertiesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertiesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.property_item, parent, false)
        return PropertiesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return propertiesList.size
    }

    fun updatePropertiesList(properties : List<Property>) {
        propertiesList.plus(properties)
    }

    override fun onBindViewHolder(holder: PropertiesViewHolder, position: Int) {
        val item = propertiesList[position]

        holder.propertyType.text = item.type
        holder.propertyPrice.text = item.price.toString()

    }

    class PropertiesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val propertyImage : ImageView = itemView.findViewById(R.id.property_item_image)
        val propertyType : TextView = itemView.findViewById(R.id.property_item_type)
        val propertyPrice : TextView = itemView.findViewById(R.id.property_item_price)

    }
}