package com.ebookfrenzy.my_dynamic_feature

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.play.core.splitcompat.SplitCompat
import com.ebookfrenzy.my_dynamic_feature.databinding.ActivityMyFeatureBinding

class MyFeatureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMyFeatureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SplitCompat.install(this)
        binding = ActivityMyFeatureBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}