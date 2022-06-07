package com.yosa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yosa.adapter.ListLevelAdapter
import com.yosa.databinding.ActivityMainBinding
import com.yosa.model.Level

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvLevel: RecyclerView
    private val list = ArrayList<Level>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        rvLevel = binding.rvLevel
        rvLevel.setHasFixedSize(true)

        list.addAll(listLevel)
        showRecyclerList()
    }

    private val listLevel: ArrayList<Level>
        get(){
            val dataName = resources.getStringArray(R.array.data_level)
            val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
            val listLevel = ArrayList<Level>()

            for (i in dataName.indices){
                val level = Level(dataName[i], dataPhoto.getResourceId(i, -1))
                listLevel.add(level)
            }
            return listLevel
        }

    private fun showRecyclerList() {
        rvLevel.layoutManager = LinearLayoutManager(this)
        val listLevelAdapter = ListLevelAdapter(list)
        rvLevel.adapter = listLevelAdapter
    }
}