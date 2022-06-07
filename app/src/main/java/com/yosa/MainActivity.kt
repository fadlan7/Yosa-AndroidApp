package com.yosa

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yosa.adapter.ListLevelAdapter
import com.yosa.databinding.ActivityMainBinding
import com.yosa.data.model.Level
import com.yosa.ui.detail.YogaDetailActivity
import com.yosa.ui.yogalist.YogaListActivity

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

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            rvLevel.layoutManager = GridLayoutManager(this, 2)
        } else {
            rvLevel.layoutManager = LinearLayoutManager(this)
        }

        listLevelAdapter.setOnItemClickCallback(object : ListLevelAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Level) {
                val intent = Intent(this@MainActivity, YogaDetailActivity::class.java)
//                intent.putExtra(YogaListActivity.EXTRA_LEVEL, data)
                startActivity(intent)
            }
        })
    }

}