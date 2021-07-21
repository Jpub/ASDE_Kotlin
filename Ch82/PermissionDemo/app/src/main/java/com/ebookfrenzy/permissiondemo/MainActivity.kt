package com.ebookfrenzy.permissiondemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import android.util.Log
import androidx.core.app.ActivityCompat
import android.app.AlertDialog

class MainActivity : AppCompatActivity() {

    private val TAG = "PermissionDemo"
    private val RECORD_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.RECORD_AUDIO)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied")

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(
                    "Permission to access the microphone is required for this app to record audio.")
                        .setTitle("Permission required")
                            builder.setPositiveButton("OK") { dialog, id ->
                        Log.i(TAG, "Clicked")
                        makeRequest()
                    }
                    val dialog = builder.create()
                dialog.show()
            } else {
                makeRequest()
            }
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            RECORD_REQUEST_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            RECORD_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.
                    PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
    }
}