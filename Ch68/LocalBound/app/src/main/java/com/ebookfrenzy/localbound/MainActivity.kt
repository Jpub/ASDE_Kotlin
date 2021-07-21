package com.ebookfrenzy.localbound

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ebookfrenzy.localbound.databinding.ActivityMainBinding
import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.os.IBinder
import android.content.Intent
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var myService: BoundService? = null
    var isBound = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = Intent(this, BoundService::class.java)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)
    }

    private val myConnection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName,
                                        service: IBinder) {
            val binder = service as BoundService.MyLocalBinder
            myService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
        }
    }

    fun showTime(view: View) {
        val currentTime = myService?.getCurrentTime()
        binding.myTextView.text = currentTime
    }
}