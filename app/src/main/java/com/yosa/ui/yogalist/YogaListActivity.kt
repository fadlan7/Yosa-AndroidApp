package com.yosa.ui.yogalist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import com.yosa.adapter.ListPoseAdapter
import com.yosa.data.model.PoseResponse
import com.yosa.data.model.PoseResponseItem
import com.yosa.data.setting.ApiConfig
import com.yosa.databinding.ActivityYogaListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class YogaListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYogaListBinding
    private lateinit var poseAdapter: ListPoseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYogaListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupRecyclerView()
        getYogaPose()
    }

    private fun setupRecyclerView() {
        poseAdapter = ListPoseAdapter(mutableListOf())

        binding.rvPose.apply {
            adapter = poseAdapter
//            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
        }
    }

    private fun getYogaPose() {
        val level = ApiConfig.getApiService().getPoses()

        level.enqueue(object : Callback<PoseResponse> {
            override fun onResponse(call: Call<PoseResponse>, response: Response<PoseResponse>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()?.poseResponse as List<PoseResponseItem>

//                    val filterNull = dataArray.filterNotNull()

                    if (dataArray != null) {
                        for (data in dataArray) {
                            poseAdapter.addPose(data)
                        }
                    }else {
                        ""
                    }
                }
            }

            override fun onFailure(call: Call<PoseResponse>, t: Throwable) {
                Toast.makeText(this@YogaListActivity, t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }
}