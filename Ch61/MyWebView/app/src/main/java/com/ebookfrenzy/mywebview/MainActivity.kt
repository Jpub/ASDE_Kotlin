package com.ebookfrenzy.mywebview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.net.URL

import com.ebookfrenzy.mywebview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleIntent()
    }

    private fun handleIntent() {
        val intent = this.intent
        val data = intent.data
        var url: URL? = null
        try {
            url = URL(data?.scheme,
                data?.host,
                data?.path)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.webView1.loadUrl(url?.toString() ?: "https://www.amazon.com")
    }
}