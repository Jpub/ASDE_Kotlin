package com.ebookfrenzy.webprint

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.ebookfrenzy.webprint.databinding.ActivityMainBinding
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebResourceRequest
import android.content.Context
import android.print.PrintAttributes
import android.print.PrintManager

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_print) {
            createWebPrintJob(binding.contentMain.myWebView)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        configureWebView()
    }

    private fun configureWebView() {
        binding.contentMain.myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView, request: WebResourceRequest): Boolean {
                return super.shouldOverrideUrlLoading(
                    view, request)
            }
        }
        binding.contentMain.myWebView.loadUrl(
            "https://www.amazon.com/")
    }

    private fun createWebPrintJob(webView: WebView?) {
        val printManager = this
            .getSystemService(Context.PRINT_SERVICE) as PrintManager
        val printAdapter = webView?.createPrintDocumentAdapter("MyDocument")
        val jobName = getString(R.string.app_name) + " Print Test"

        printAdapter?.let {
            printManager.print(
                jobName, it,
                PrintAttributes.Builder().build()
            )
        }
    }
}