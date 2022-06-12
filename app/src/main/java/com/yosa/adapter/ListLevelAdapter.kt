package com.yosa.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yosa.data.model.Level
import com.yosa.databinding.ItemCardBinding
import com.yosa.ui.detail.YogaDetailActivity
import com.yosa.ui.yogalist.YogaListActivity

class ListLevelAdapter(private val listLevel: ArrayList<Level>) :
    RecyclerView.Adapter<ListLevelAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listLevel[position])
    }

    override fun getItemCount(): Int {
        return listLevel.size
    }

    class ListViewHolder(private var binding: ItemCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(level: Level) {
            binding.apply {
                imgLevel.setImageResource(level.photo)
                tvLevel.text = level.levelName

                itemView.setOnClickListener {
                    Intent(itemView.context, YogaDetailActivity::class.java).also {
                        itemView.context.startActivity(it)
                    }
                }
            }
        }
    }
}