package com.ebookfrenzy.explicitintent

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent

import com.ebookfrenzy.explicitintent.databinding.ActivityBBinding

class ActivityB : AppCompatActivity() {

    private lateinit var binding: ActivityBBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val extras = intent.extras ?: return
        val qString = extras.getString("qString")
        binding.textView2.text = qString
    }

    fun returnText(view: View) {
        finish()
    }

    override fun finish() {
        val data = Intent()
        val returnString = binding.editText2.text.toString()
        data.putExtra("returnData", returnString)
        setResult(RESULT_OK, data)
        super.finish()
    }
}