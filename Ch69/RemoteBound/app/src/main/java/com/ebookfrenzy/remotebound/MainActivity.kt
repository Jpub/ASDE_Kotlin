package com.ebookfrenzy.remotebound

import androidx.appcompat.app.AppCompatActivity
import android.content.ComponentName
import android.content.ServiceConnection
import android.os.*
import android.view.View
import android.content.Context
import android.content.Intent

class MainActivity : AppCompatActivity() {

    var myService: Messenger? = null
    var isBound: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intent = Intent(applicationContext, RemoteService::class.java)
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE)
    }

    fun sendMessage(view: View) {
        if (!isBound) return

        val msg = Message.obtain()
        val bundle = Bundle()

        bundle.putString("MyString", "Message Received")

        msg.data = bundle

        try {
            myService?.send(msg)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
    }

    private val myConnection = object : ServiceConnection {

        override fun onServiceConnected(
            className: ComponentName,
            service: IBinder) {
            myService = Messenger(service)
            isBound = true
        }

        override fun onServiceDisconnected(
            className: ComponentName) {
            myService = null
            isBound = false
        }
    }
}