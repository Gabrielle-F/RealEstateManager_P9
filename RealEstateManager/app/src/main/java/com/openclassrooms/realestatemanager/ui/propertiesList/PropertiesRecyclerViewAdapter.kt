package com.openclassrooms.realestatemanager.ui.propertiesList

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.DaggerHiltApplication
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.utils.Utils

class PropertiesRecyclerViewAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<PropertiesRecyclerViewAdapter.PropertiesViewHolder>() {

    private val list = mutableListOf<Property>()
    private var selectedCurrency : String = "Dollar"

    interface OnItemClickListener {
        fun onClick(property: Property)
        fun getSharedPreferences(): String
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertiesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_property, parent, false)
        return PropertiesViewHolder(view, onItemClickListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePropertiesList(properties : List<Property>) {
        list.clear()
        list.addAll(properties)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PropertiesViewHolder, position: Int) {
        val item = list[position]
        val sharedPreferences = onItemClickListener.getSharedPreferences()
        holder.property = item
        holder.propertyType.text = item.type
        holder.propertyPrice.text = item.price.toString()
        if(sharedPreferences == "Euro") {
            Utils.convertEuroToDollar(item.price)
        } else if (sharedPreferences == "Dollar") {
            Utils.convertEuroToDollar(item.price)
        }

        val firstPicture = item.getFirstImage()
        val uri = Uri.parse(firstPicture?.imageUri ?: "")
        if(uri != null) {
            holder.propertyImage.setImageURI(uri)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class PropertiesViewHolder(itemView: View, private val onItemClickListener: OnItemClickListener) : RecyclerView.ViewHolder(itemView) {
        val propertyImage : ImageView = itemView.findViewById(R.id.property_item_image)
        val propertyType : TextView = itemView.findViewById(R.id.property_item_type)
        val propertyPrice : TextView = itemView.findViewById(R.id.property_item_price)
        var property: Property? = null

        init {
            itemView.setOnClickListener {
                val property = property
                if(property != null) {
                    onItemClickListener.onClick(property)
                }
            }
        }
    }
}