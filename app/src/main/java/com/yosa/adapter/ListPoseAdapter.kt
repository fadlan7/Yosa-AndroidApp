package com.yosa.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yosa.Communicator
import com.yosa.Constanta.EXTRA_POSE_DESC
import com.yosa.Constanta.EXTRA_POSE_IMG
import com.yosa.Constanta.EXTRA_POSE_LEVEL
import com.yosa.Constanta.EXTRA_POSE_NAME
import com.yosa.R
import com.yosa.data.model.PosesItem
import com.yosa.databinding.ItemCardBinding
import com.yosa.ui.detail.YogaDetailActivity
import com.yosa.ui.detail.poseinfo.PoseInfoFragment

class ListPoseAdapter(private val listPose: MutableList<PosesItem>) :
    RecyclerView.Adapter<ListPoseAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val pose = listPose[position]

        holder.apply {
            poseName.text = pose.namePose

            Glide.with(holder.itemView.context)
                .load(pose.images.lastIndex)
                .placeholder(R.drawable.ic_beginnerimg)
                .error(R.drawable.ic_beginnerimg)
                .into(imgLevel)

            itemView.setOnClickListener {
                val moveToDetail = Intent(itemView.context, YogaDetailActivity::class.java)

                moveToDetail.putExtra(EXTRA_POSE_NAME, pose.namePose)
                moveToDetail.putExtra(EXTRA_POSE_DESC, pose.descPose)
                moveToDetail.putExtra(EXTRA_POSE_LEVEL, pose.level.nameLevel)
                moveToDetail.putExtra(EXTRA_POSE_IMG, pose.images.lastIndex)
                itemView.context.startActivity(moveToDetail)
            }
        }
    }


    override fun getItemCount(): Int {
        return listPose.size
    }

    fun addPose(newLevel: PosesItem) {
        listPose.add(newLevel)
        notifyItemInserted(listPose.lastIndex)
    }

    class ListViewHolder(binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root) {
        var imgLevel: ImageView = binding.imgPose
        var poseName: TextView = binding.tvPoseName
    }
}