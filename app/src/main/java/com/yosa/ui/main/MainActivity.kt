package com.yosa.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
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
import com.yosa.data.model.LevelResponse
import com.yosa.data.model.LevelsItem
import com.yosa.data.setting.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "after_onboard")
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var yogaAdapter: ListLevelAdapter

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        supportActionBar?.hide()

        setupViewModel()
        setupRecyclerView()
        getYogaLevel()
//        getLevel()
    }

    private fun setupViewModel() {
        val pref = PreferenceDataStore.getInstance(dataStore)
        mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

        mainViewModel.getOnboard().observe(this) {}
    }

    private fun setupRecyclerView() {
        yogaAdapter = ListLevelAdapter(mutableListOf())

        binding.rvLevel.apply {
            adapter = yogaAdapter
//            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            isNestedScrollingEnabled = false
        }
    }

//    @SuppressLint("Recycle")
//    private fun getLevel() {
//        val dataName = resources.getStringArray(R.array.data_level)
//        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
//        for (i in dataName.indices) {
//            val level = LevelLocal(dataName[i], dataPhoto.getResourceId(i, -1))
//            this@MainActivity.listLevel.add(level)
//        }
//    }

    private fun getYogaLevel() {
        val level = ApiConfig.getApiService().getLevels()

        level.enqueue(object : Callback<LevelResponse> {
            override fun onResponse(call: Call<LevelResponse>, response: Response<LevelResponse>) {
                if (response.isSuccessful) {
                    val dataArray = response.body()?.levels as List<LevelsItem>

                    for (data in dataArray) {
                        yogaAdapter.addLevel(data)
                    }
                }
            }

            override fun onFailure(call: Call<LevelResponse>, t: Throwable) {
                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                t.printStackTrace()
            }
        })
    }
}