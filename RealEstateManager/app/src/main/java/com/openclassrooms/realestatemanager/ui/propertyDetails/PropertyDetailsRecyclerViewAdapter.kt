package com.openclassrooms.realestatemanager.ui.propertyDetails

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.LocalPicture

class PropertyDetailsRecyclerViewAdapter : RecyclerView.Adapter<PropertyDetailsRecyclerViewAdapter.PropertyViewHolder>() {

    private val list = mutableListOf<LocalPicture>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pictures_property_details_fragment, parent, false)
        return PropertyViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePicturesList(pictures: List<LocalPicture>) {
        list.clear()
        list.addAll(pictures)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val item = list[position]
        holder.picture = item
        holder.pictureView.setImageURI(Uri.parse(item.imageUrl))
        holder.pictureTitle.setText(item.imageTitle)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class PropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pictureView : ImageView = itemView.findViewById(R.id.property_details_picture_item)
        val pictureTitle : TextView = itemView.findViewById(R.id.property_details_picture_title)
        var picture : LocalPicture? = null
    }
}