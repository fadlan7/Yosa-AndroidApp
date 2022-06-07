package com.yosa.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yosa.R
import com.yosa.model.Level

class ListLevelAdapter(private val listLevel: ArrayList<Level>) :
    RecyclerView.Adapter<ListLevelAdapter.ListViewHolder>() {

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgPhoto: ImageView = itemView.findViewById(R.id.iv_photo)
        var tvItem: TextView = itemView.findViewById(R.id.tv_item_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_card, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (itemName, photo) = listLevel[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvItem.text = itemName
    }

    override fun getItemCount(): Int = listLevel.size
}