package com.ebookfrenzy.sendbroadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        val message = "Broadcast intent detected " + intent.action
        Toast.makeText(context, message,
            Toast.LENGTH_LONG).show()
    }
}