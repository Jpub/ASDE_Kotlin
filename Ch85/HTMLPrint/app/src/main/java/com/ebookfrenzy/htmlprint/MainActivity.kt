package com.ebookfrenzy.htmlprint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.print.PrintAttributes
import android.print.PrintManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.content.Context

class MainActivity : AppCompatActivity() {

    private var myWebView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        printWebView()
    }

    private fun printWebView() {
        val webView = WebView(this)

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView,
                                                  request: WebResourceRequest): Boolean {
                return false
            }

            override fun onPageFinished(view: WebView, url: String) {
                createWebPrintJob(view)
                myWebView = null
            }
        }

        val htmlDocument = "<html><body><h1>Android Print Test</h1><p>" +
                "This is some sample content.</p></body></html>"
        webView.loadDataWithBaseURL(null, htmlDocument,
            "text/HTML", "UTF-8", null)
        myWebView = webView
    }

    private fun createWebPrintJob(webView: WebView) {
        val printManager = this
            .getSystemService(Context.PRINT_SERVICE) as PrintManager

        val printAdapter = webView.createPrintDocumentAdapter("MyDocument")

        val jobName = getString(R.string.app_name) + " Print Test"

        printManager.print(jobName, printAdapter,
            PrintAttributes.Builder().build())
    }
}