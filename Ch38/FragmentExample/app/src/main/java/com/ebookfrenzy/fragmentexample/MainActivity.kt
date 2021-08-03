package com.ebookfrenzy.fragmentexample

import androidx.fragment.app.FragmentActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : FragmentActivity(),
    ToolbarFragment.ToolbarListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onButtonClick(fontsize: Int, text: String) {
        val textFragment = supportFragmentManager.findFragmentById(
            R.id.text_fragment) as TextFragment
        textFragment.changeTextProperties(fontsize, text)
    }
}