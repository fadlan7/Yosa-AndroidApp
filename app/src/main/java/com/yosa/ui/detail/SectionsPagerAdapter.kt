package com.yosa.ui.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yosa.ui.detail.posedetection.PoseDetectionFragment
import com.yosa.ui.detail.poseinfo.PoseInfoFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val detail: String) :
    FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        fragment = when (position) {
            0 -> PoseInfoFragment.newInstance(detail)
            else -> PoseDetectionFragment.newInstance(detail)
        }
        return fragment
    }
}