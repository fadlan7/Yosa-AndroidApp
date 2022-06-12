package com.yosa.ui.yogalist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yosa.databinding.ActivityYogaListBinding
import com.yosa.data.model.Level

class YogaListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityYogaListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYogaListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val level = intent.getParcelableExtra<Level>(EXTRA_LEVEL) as Level

        binding.apply {
            tvLevel.text = level.levelName
        }
    }

    companion object {
        const val EXTRA_LEVEL = "extra_level"
    }
}