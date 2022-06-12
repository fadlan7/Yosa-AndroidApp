package com.yosa.ui.onboarding


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yosa.data.PreferenceDataStore
import kotlinx.coroutines.launch

class OnboardViewModel(private val pref: PreferenceDataStore): ViewModel() {
    fun getOnboard(): LiveData<Boolean> {
        return pref.getOnboard().asLiveData()
    }

    fun saveOnboard(save: Boolean) {
        viewModelScope.launch {
            pref.saveOnboard(save)
        }
    }
}