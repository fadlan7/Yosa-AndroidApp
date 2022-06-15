package com.yosa.ui.detail.poseinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yosa.Constanta.EXTRA_DETAIL
import com.yosa.Constanta.EXTRA_POSE_DESC
import com.yosa.Constanta.EXTRA_POSE_IMG
import com.yosa.Constanta.EXTRA_POSE_LEVEL
import com.yosa.Constanta.EXTRA_POSE_NAME
import com.yosa.R
import com.yosa.databinding.FragmentPoseInfoBinding

class PoseInfoFragment : Fragment() {
    private var param1: String? = null
    private lateinit var binding: FragmentPoseInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(EXTRA_DETAIL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPoseInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val poseName = arguments?.getString(EXTRA_POSE_NAME)
        val poseDesc = arguments?.getString(EXTRA_POSE_DESC)
        val poseLevel = arguments?.getString(EXTRA_POSE_LEVEL)
        val poseImg = arguments?.getString(EXTRA_POSE_IMG)
        binding.apply {
            tvPoseName.text = poseName
            tvPoseDesc.text = poseDesc
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            PoseInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_DETAIL, param1)
                }
            }
    }
}