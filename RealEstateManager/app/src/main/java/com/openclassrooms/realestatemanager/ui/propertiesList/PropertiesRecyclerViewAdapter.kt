package com.openclassrooms.realestatemanager.ui.propertiesList

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Property
import com.openclassrooms.realestatemanager.utils.Utils

class PropertiesRecyclerViewAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<PropertiesRecyclerViewAdapter.PropertiesViewHolder>() {

    private val list = mutableListOf<Property>()
    private var selectedCurrency: String = ""

    interface OnItemClickListener {
        fun onClick(property: Property)
        fun getSharedPreferences(): String
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertiesViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_property, parent, false)
        return PropertiesViewHolder(view, onItemClickListener)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePropertiesList(properties: List<Property>) {
        list.clear()
        list.addAll(properties)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateSelectedCurrency(currency: String) {
        selectedCurrency = currency
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PropertiesViewHolder, position: Int) {
        val item = list[position]
        selectedCurrency = onItemClickListener.getSharedPreferences()
        holder.property = item
        holder.propertyType.text = item.type
        val price = item.price
        when (selectedCurrency) {
            "Euro" -> {
                holder.propertyPriceEuro.visibility = View.VISIBLE
                holder.propertyPriceDollar.visibility = View.GONE
                Utils.convertDollarToEuro(price)
            }
            "Dollar" -> {
                holder.propertyPriceDollar.visibility = View.VISIBLE
                holder.propertyPriceEuro.visibility = View.GONE
                Utils.convertEuroToDollar(price)
            }
        }
        holder.propertyPrice.text = item.price.toString()

        item.pictures.firstOrNull()?.let {
            holder.propertyImage.setImageURI(Uri.parse(it.imageUrl))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class PropertiesViewHolder(
        itemView: View,
        private val onItemClickListener: OnItemClickListener
    ) : RecyclerView.ViewHolder(itemView) {
        val propertyImage: ImageView = itemView.findViewById(R.id.property_item_image)
        val propertyType: TextView = itemView.findViewById(R.id.property_item_type)
        val propertyPrice: TextView = itemView.findViewById(R.id.property_item_price)
        val propertyPriceDollar: ImageView = itemView.findViewById(R.id.property_item_dollar_icon)
        val propertyPriceEuro: ImageView = itemView.findViewById(R.id.property_item_euro_icon)
        var property: Property? = null

        init {
            itemView.setOnClickListener {
                val property = property
                property?.let {
                    onItemClickListener.onClick(property)
                }
            }
        }
    }
}