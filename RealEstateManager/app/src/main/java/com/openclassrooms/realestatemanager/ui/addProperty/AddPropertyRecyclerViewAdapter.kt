package com.openclassrooms.realestatemanager.ui.addProperty

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Image

class AddPropertyRecyclerViewAdapter :
    RecyclerView.Adapter<AddPropertyRecyclerViewAdapter.PicturesViewHolder>() {

    private val picturesList = mutableListOf<Image>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PicturesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pictures_add_property_activity, parent, false)
        return PicturesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return picturesList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePicturesList(pictures: List<Image>) {
        picturesList.clear()
        picturesList.addAll(pictures)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: PicturesViewHolder, position: Int) {
        val pictureItem = picturesList[position]
        val uri = Uri.parse(pictureItem.imageUri)
        holder.imageView.setImageURI(uri)
    }

    class PicturesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView as ImageView
    }
}