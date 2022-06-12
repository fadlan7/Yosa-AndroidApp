package com.yosa.ui.onboarding

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.yosa.ui.main.MainActivity
import com.yosa.ViewModelFactory
import com.yosa.data.PreferenceDataStore
import com.yosa.databinding.ActivityOnboardBinding

class OnboardActivity : AppCompatActivity() {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "onboard_features")
    private lateinit var binding: ActivityOnboardBinding
    private lateinit var onboardViewModel: OnboardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        setupViewModel()
        playAnimation()
    }

    private fun setupViewModel() {
        val pref = PreferenceDataStore.getInstance(dataStore)
        onboardViewModel = ViewModelProvider(this, ViewModelFactory(pref))[OnboardViewModel::class.java]

        onboardViewModel.getOnboard().observe(this) { save: Boolean ->
            if(save) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        binding.btnToHome.setOnClickListener {
            onboardViewModel.saveOnboard(true)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun playAnimation() {
        val picture = ObjectAnimator.ofFloat(binding.imgOnboard1, View.ALPHA, 1f).setDuration(520)
        val title = ObjectAnimator.ofFloat(binding.tvTitle1, View.ALPHA, 1f).setDuration(520)
        val desc = ObjectAnimator.ofFloat(binding.tvDescription1, View.ALPHA, 1f).setDuration(520)

        AnimatorSet().apply {
            playSequentially(picture, title, desc)
            start()
        }
    }
}