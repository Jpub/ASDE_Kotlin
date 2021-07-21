package com.ebookfrenzy.customgestures

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.gesture.GestureLibraries
import android.gesture.GestureLibrary
import android.gesture.GestureOverlayView
import android.gesture.GestureOverlayView.OnGesturePerformedListener

import com.ebookfrenzy.customgestures.databinding.ActivityMainBinding
import android.widget.Toast
import android.gesture.Gesture

class MainActivity : AppCompatActivity(), OnGesturePerformedListener {

    private lateinit var binding: ActivityMainBinding
    var gLibrary: GestureLibrary? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        gestureSetup()
    }

    private fun gestureSetup() {
        gLibrary = GestureLibraries.fromRawResource(this,
            R.raw.gestures)
        if (gLibrary?.load() == false) {
            finish()
        }

        binding.gOverlay.addOnGesturePerformedListener(this)
    }

    override fun onGesturePerformed(overlay: GestureOverlayView,
                                    gesture: Gesture) {
        val predictions = gLibrary?.recognize(gesture)
        predictions?.let {
            if (it.size > 0 && it[0].score > 1.0) {
                val action = it[0].name
                Toast.makeText(this, action, Toast.LENGTH_SHORT).show()
            }
        }
    }
}