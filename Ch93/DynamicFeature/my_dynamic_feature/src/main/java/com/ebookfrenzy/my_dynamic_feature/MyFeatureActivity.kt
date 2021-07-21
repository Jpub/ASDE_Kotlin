package com.ebookfrenzy.my_dynamic_feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ebookfrenzy.my_dynamic_feature.databinding.ActivityMyFeatureBinding
import com.google.android.play.core.splitcompat.SplitCompat

class MyFeatureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyFeatureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SplitCompat.install(this)
        binding = ActivityMyFeatureBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}