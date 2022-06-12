package com.yosa.ui.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yosa.R
import com.yosa.ViewModelFactory
import com.yosa.adapter.ListLevelAdapter
import com.yosa.data.PreferenceDataStore
import com.yosa.databinding.ActivityMainBinding
import com.yosa.data.model.Level

class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "after_onboard")
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var yogaAdapter: ListLevelAdapter
    private val listLevel = ArrayList<Level>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setupViewModel()
        setupRecyclerView()
        getLevel()
    }

    private fun setupViewModel() {
        val pref = PreferenceDataStore.getInstance(dataStore)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

        mainViewModel.getOnboard().observe(this){}
    }

    private fun setupRecyclerView() {
        yogaAdapter = ListLevelAdapter(listLevel)

        binding.rvLevel.apply {
            adapter = yogaAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
        }
    }

    @SuppressLint("Recycle")
    private fun getLevel() {
        val dataName = resources.getStringArray(R.array.data_level)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        for(i in dataName.indices) {
            val level = Level(dataName[i], dataPhoto.getResourceId(i, -1))
            this@MainActivity.listLevel.add(level)
        }
    }

}