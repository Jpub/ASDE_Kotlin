package com.ebookfrenzy.implicitintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ebookfrenzy.implicitintent.databinding.ActivityMainBinding
import android.content.Intent
import android.view.View
import android.net.Uri

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showWebPage(view: View) {
        val intent = Intent(Intent.ACTION_VIEW,
            Uri.parse("https://www.ebookfrenzy.com"))
        startActivity(intent)
    }
}