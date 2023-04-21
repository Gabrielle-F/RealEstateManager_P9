package com.openclassrooms.realestatemanager.ui.addProperty

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Image

class AddPropertyRecyclerViewAdapter(private val picturesList : List<Image>) :
    RecyclerView.Adapter<AddPropertyRecyclerViewAdapter.PicturesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pictures_add_property_activity_item, parent, false)
        return PicturesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return picturesList.size
    }

    fun updatePicturesList(pictures: List<Image>) {
        picturesList.plus(pictures)
    }

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        val pictureItem = picturesList[position]
        holder.imageView.setImageResource(pictureItem.imageUri.toInt())
    }

    class PicturesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView : ImageView = itemView.findViewById(R.id.add_property_activity_picture_item_view)
    }
}