package com.yosa.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.yosa.data.PreferenceDataStore

class MainViewModel(private val pref: PreferenceDataStore) : ViewModel() {

    fun getOnboard(): LiveData<Boolean> {
        return pref.getOnboard().asLiveData()
    }
}