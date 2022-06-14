package com.yosa.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.yosa.Constanta.EXTRA_LEVELS
import com.yosa.R
import com.yosa.data.model.LevelsItem
import com.yosa.databinding.ItemCardBinding
import com.yosa.databinding.ItemCardLevelBinding
import com.yosa.ui.yogalist.YogaListActivity

class ListLevelAdapter(private val listLevel: MutableList<LevelsItem>) :
    RecyclerView.Adapter<ListLevelAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemCardLevelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val levels = listLevel[position]

        holder.apply {
            levelName.text =levels.nameLevel

            itemView.setOnClickListener {
                val moveToList = Intent(itemView.context, YogaListActivity::class.java)

                moveToList.putExtra(EXTRA_LEVELS, levels.nameLevel)
                itemView.context.startActivity(moveToList)
            }
        }
    }

    override fun getItemCount(): Int {
        return listLevel.size
    }

    fun addLevel(newLevel: LevelsItem) {
        listLevel.add(newLevel)
        notifyItemInserted(listLevel.lastIndex)
    }

    class ListViewHolder(binding: ItemCardLevelBinding) : RecyclerView.ViewHolder(binding.root) {
//        var imgLevel: ImageView = binding.imgLevel
        var levelName: TextView = binding.tvLevel
    }
}