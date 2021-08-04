package com.ebookfrenzy.serviceexample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.ebookfrenzy.serviceexample.databinding.ActivityMainBinding

import android.view.View
import androidx.core.app.JobIntentService.enqueueWork
import android.content.Intent

class MainActivity : AppCompatActivity() {
    val SERVICE_ID = 1001
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun buttonClick(view: View) {
        intent = Intent(this, MyService::class.java)
        startService(intent)
    }
}