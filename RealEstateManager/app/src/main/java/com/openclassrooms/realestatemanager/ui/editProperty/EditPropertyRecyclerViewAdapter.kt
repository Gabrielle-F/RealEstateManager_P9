package com.openclassrooms.realestatemanager.ui.editProperty

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.model.Image

class EditPropertyRecyclerViewAdapter : RecyclerView.Adapter<EditPropertyRecyclerViewAdapter.EditPropertyViewHolder>() {

    private val list = mutableListOf<Image>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EditPropertyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pictures_add_property_activity, parent, false)
        return EditPropertyViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updatePicturesList(pictures: List<Image>) {
        list.clear()
        list.addAll(pictures)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: EditPropertyViewHolder, position: Int) {
        val pictureItem = list[position]
        val uri = Uri.parse(pictureItem.imageUri)
        holder.imageView.setImageURI(uri)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class EditPropertyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView = itemView as ImageView
    }
}