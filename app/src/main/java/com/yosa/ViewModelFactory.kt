package com.yosa

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yosa.data.PreferenceDataStore
import com.yosa.ui.main.MainViewModel
import com.yosa.ui.onboarding.OnboardViewModel

class ViewModelFactory(private val pref: PreferenceDataStore): ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(OnboardViewModel::class.java) -> {
                OnboardViewModel(pref) as T
            }
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}