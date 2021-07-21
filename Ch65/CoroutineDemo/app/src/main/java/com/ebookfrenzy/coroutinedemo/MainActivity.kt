package com.ebookfrenzy.coroutinedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ebookfrenzy.coroutinedemo.databinding.ActivityMainBinding
import android.widget.SeekBar
import kotlinx.coroutines.*
import android.view.View

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var count: Int = 1
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.seekBar.setOnSeekBarChangeListener(object :
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar,
                                           progress: Int, fromUser: Boolean) {
                count = progress
                binding.countText.text = "${count} coroutines"
            }
            override fun onStartTrackingTouch(seek: SeekBar) {
            }
            override fun onStopTrackingTouch(seek: SeekBar) {
            }
        })
    }

    suspend fun performTask(tasknumber: Int): Deferred<String> =
        coroutineScope.async(Dispatchers.Main) {
            delay(5_000)
            return@async "Finished Coroutine ${tasknumber}"
        }

    fun launchCoroutines(view: View) {
        (1..count).forEach {
            binding.statusText.text = "Started Coroutine ${it}"
            coroutineScope.launch(Dispatchers.Main) {
                binding.statusText.text = performTask(it).await()
            }
        }
    }
}