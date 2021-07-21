package com.ebookfrenzy.serviceexample

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import kotlinx.coroutines.*

class MyService : Service() {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)
    private val TAG = "ServiceExample"

    override fun onCreate() {
        Log.i(TAG, "Service onCreate")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        coroutineScope.launch(Dispatchers.Default) {
            performTask(startId)
        }

        return Service.START_STICKY
    }

    suspend fun performTask(startId: Int) {
        Log.i(TAG, "Service onStartCommand " + startId)
        var i: Int = 0
        while (i <= 3) {
            try {
                delay(10_000)
                i++
            } catch (e: Exception) {
            }
            Log.i(TAG, "Service running " + startId)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        Log.i(TAG, "Service onBind")
        TODO("Return the communication channel to the service.")
    }

    override fun onDestroy() {
        Log.i(TAG, "Service onDestroy")
    }
}